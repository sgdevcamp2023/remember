package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.common.dto.SearchDmUserStateFeignResponse;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.feign.UserStatusClient;
import harmony.communityservice.community.domain.Room;
import harmony.communityservice.community.domain.User;
import harmony.communityservice.community.mapper.ToRoomResponseDtoMapper;
import harmony.communityservice.community.mapper.ToSearchUserStateResponseMapper;
import harmony.communityservice.community.mapper.ToUserIdsMapper;
import harmony.communityservice.community.query.dto.SearchRoomResponse;
import harmony.communityservice.community.query.dto.SearchRoomsResponse;
import harmony.communityservice.community.query.dto.SearchUserStateResponse;
import harmony.communityservice.community.query.dto.SearchUserStatusInDmRoomRequest;
import harmony.communityservice.community.query.repository.RoomQueryRepository;
import harmony.communityservice.community.query.service.RoomQueryService;
import harmony.communityservice.community.query.service.UserQueryService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoomQueryServiceImpl implements RoomQueryService {

    private final UserQueryService userQueryService;

    private final RoomQueryRepository roomQueryRepository;
    private final UserStatusClient userStatusClient;

    @Override
    public SearchRoomsResponse searchList(long userId) {
        List<Room> rooms = roomQueryRepository.findRoomsByUserIdsContains(userId);
        List<SearchRoomResponse> searchRoomResponses = rooms.stream()
                .map(ToRoomResponseDtoMapper::convert).toList();
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
