package harmony.communityservice.room.service.query.impl;

import harmony.communityservice.common.client.UserStatusClient;
import harmony.communityservice.common.dto.SearchDmUserStateFeignResponse;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.room.domain.Room;
import harmony.communityservice.room.domain.RoomId;
import harmony.communityservice.room.domain.RoomUser;
import harmony.communityservice.room.dto.SearchRoomResponse;
import harmony.communityservice.room.dto.SearchRoomsResponse;
import harmony.communityservice.room.dto.SearchUserStateResponse;
import harmony.communityservice.room.dto.SearchUserStatusInDmRoomRequest;
import harmony.communityservice.room.mapper.ToSearchRoomResponseMapper;
import harmony.communityservice.room.mapper.ToSearchUserStateResponseMapper;
import harmony.communityservice.room.mapper.ToUserIdsMapper;
import harmony.communityservice.room.repository.query.RoomQueryRepository;
import harmony.communityservice.room.service.query.RoomQueryService;
import harmony.communityservice.user.adapter.out.persistence.UserJpaEntity;
import harmony.communityservice.user.adapter.out.persistence.UserId;
import harmony.communityservice.user.service.query.UserQueryService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        List<Room> rooms = roomQueryRepository.findRoomsByUserIdsContains(UserId.make(userId));
        List<SearchRoomResponse> searchRoomResponses = rooms.stream()
                .map(ToSearchRoomResponseMapper::convert).toList();
        return new SearchRoomsResponse(searchRoomResponses);
    }

    @Override
    public List<Long> searchRoomIdsByUserId(long userId) {
        List<Room> rooms = roomQueryRepository.findRoomsByUserIdsContains(UserId.make(userId));
        return rooms.stream()
                .map(Room::getRoomId)
                .map(RoomId::getId).toList();
    }

    @Override
    public Map<Long, ?> searchUserStatesInRoom(long roomId) {
        Room targetRoom = roomQueryRepository.findByRoomId(RoomId.make(roomId)).orElseThrow(NotFoundDataException::new);
        List<UserJpaEntity> users = targetRoom.getRoomUsers()
                .stream()
                .map(RoomUser::getUserId)
                .map(userQueryService::searchByUserId)
                .collect(Collectors.toList());
        return makeCurrentUserStates(users);
    }

    private Map<Long, SearchUserStateResponse> makeCurrentUserStates(List<UserJpaEntity> users) {
        Map<Long, SearchUserStateResponse> userStates = new HashMap<>();
        for (UserJpaEntity user : users) {
            SearchUserStateResponse searchUserStateResponse = ToSearchUserStateResponseMapper.convert(user,
                    getConnectionStates(users).get(user.getUserId().getId()));
            userStates.put(user.getUserId().getId(), searchUserStateResponse);
        }
        return userStates;
    }

    private Map<Long, String> getConnectionStates(List<UserJpaEntity> users) {
        List<Long> userIds = users
                .stream()
                .map(ToUserIdsMapper::convert)
                .map(UserId::getId)
                .toList();
        SearchUserStatusInDmRoomRequest searchUserStatusInDmRoomRequest = new SearchUserStatusInDmRoomRequest(userIds);
        SearchDmUserStateFeignResponse searchDmUserStateFeignResponse = userStatusClient.getCommunityUsersState(
                searchUserStatusInDmRoomRequest);
        return searchDmUserStateFeignResponse.getConnectionStates();
    }
}
