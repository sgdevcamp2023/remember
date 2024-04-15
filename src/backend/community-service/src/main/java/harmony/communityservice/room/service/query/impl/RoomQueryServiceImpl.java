package harmony.communityservice.room.service.query.impl;

import harmony.communityservice.common.client.UserStatusClient;
import harmony.communityservice.common.dto.SearchDmUserStateFeignResponse;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.room.mapper.ToSearchRoomResponseMapper;
import harmony.communityservice.room.mapper.ToUserIdsMapper;
import harmony.communityservice.room.domain.Room;
import harmony.communityservice.room.dto.SearchRoomResponse;
import harmony.communityservice.room.dto.SearchRoomsResponse;
import harmony.communityservice.room.dto.SearchUserStatusInDmRoomRequest;
import harmony.communityservice.room.repository.query.RoomQueryRepository;
import harmony.communityservice.room.service.query.RoomQueryService;
import harmony.communityservice.user.domain.User;
import harmony.communityservice.room.dto.SearchUserStateResponse;
import harmony.communityservice.room.mapper.ToSearchUserStateResponseMapper;
import harmony.communityservice.user.service.query.UserQueryService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomQueryServiceImpl implements RoomQueryService {

    private final UserQueryService userQueryService;

    private final RoomQueryRepository roomQueryRepository;
    private final UserStatusClient userStatusClient;

    @Override
    public SearchRoomsResponse searchList(long userId) {
        List<Room> rooms = roomQueryRepository.findRoomsByUserIdsContains(userId);
        List<SearchRoomResponse> searchRoomResponses = rooms.stream()
                .map(ToSearchRoomResponseMapper::convert).toList();
        return new SearchRoomsResponse(searchRoomResponses);
    }

    @Override
    public List<Long> searchRoomIdsByUserId(long userId) {
        List<Room> rooms = roomQueryRepository.findRoomsByUserIdsContains(userId);
        return rooms.stream()
                .map(Room::getRoomId).toList();
    }

    @Override
    public Map<Long, ?> searchUserStatesInRoom(long roomId) {
        Room targetRoom = roomQueryRepository.findByRoomId(roomId).orElseThrow(NotFoundDataException::new);
        Set<Long> userIds = targetRoom.getUserIds();
        List<User> users = userIds.stream()
                .map(userQueryService::searchByUserId)
                .collect(Collectors.toList());
        return makeCurrentUserStates(users);
    }

    private Map<Long, SearchUserStateResponse> makeCurrentUserStates(List<User> users) {
        Map<Long, SearchUserStateResponse> userStates = new HashMap<>();
        for (User user : users) {
            SearchUserStateResponse searchUserStateResponse = ToSearchUserStateResponseMapper.convert(user,
                    getConnectionStates(users).get(user.getUserId()));
            userStates.put(user.getUserId(), searchUserStateResponse);
        }
        return userStates;
    }

    private Map<Long, String> getConnectionStates(List<User> users) {
        List<Long> userIds = users
                .stream()
                .map(ToUserIdsMapper::convert)
                .toList();
        SearchUserStatusInDmRoomRequest searchUserStatusInDmRoomRequest = new SearchUserStatusInDmRoomRequest(userIds);
        SearchDmUserStateFeignResponse searchDmUserStateFeignResponse = userStatusClient.getCommunityUsersState(
                searchUserStatusInDmRoomRequest);
        return searchDmUserStateFeignResponse.getConnectionStates();
    }
}
