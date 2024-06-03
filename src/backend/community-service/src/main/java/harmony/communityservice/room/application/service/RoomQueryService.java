package harmony.communityservice.room.application.service;

import harmony.communityservice.common.annotation.UseCase;
import harmony.communityservice.common.client.UserStatusClient;
import harmony.communityservice.common.dto.LoadDmUserStateFeignResponse;
import harmony.communityservice.room.application.port.in.LoadRoomIdsQuery;
import harmony.communityservice.room.application.port.in.LoadRoomsQuery;
import harmony.communityservice.room.application.port.in.LoadUserStatesInRoomQuery;
import harmony.communityservice.room.application.port.in.SearchRoomsResponse;
import harmony.communityservice.room.application.port.out.LoadRoomIdsPort;
import harmony.communityservice.room.application.port.out.LoadRoomPort;
import harmony.communityservice.room.application.port.out.LoadRoomsPort;
import harmony.communityservice.room.domain.Room;
import harmony.communityservice.room.domain.Room.RoomId;
import harmony.communityservice.room.application.port.in.LoadUserStateResponse;
import harmony.communityservice.common.dto.LoadUserStatusInDmRoomRequest;
import harmony.communityservice.user.application.port.in.LoadUserQuery;
import harmony.communityservice.user.domain.User;
import harmony.communityservice.user.domain.User.UserId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class RoomQueryService implements LoadRoomsQuery, LoadUserStatesInRoomQuery, LoadRoomIdsQuery {

    private final UserStatusClient userStatusClient;
    private final LoadRoomsPort loadRoomsPort;
    private final LoadRoomPort loadRoomPort;
    private final LoadUserQuery loadUserUseCase;
    private final LoadRoomIdsPort loadRoomIdsPort;

    @Override
    public SearchRoomsResponse loadList(Long id) {
        UserId userId = UserId.make(id);
        List<Room> rooms = loadRoomsPort.loadRooms(userId);
        return new SearchRoomsResponse(rooms);
    }

    @Override
    public List<Long> loadRoomIds(Long userId) {
        return loadRoomIdsPort.loadRoomIds(UserId.make(userId));
    }

    @Override
    public Map<Long, LoadUserStateResponse> loadUserStates(Long dmId) {
        RoomId roomId = RoomId.make(dmId);
        Room room = loadRoomPort.loadByRoomId(roomId);
        List<User> users = room.getRoomUsers()
                .stream()
                .map(roomUser -> roomUser.getUserId().getId())
                .map(loadUserUseCase::loadUser)
                .toList();
        return makeCurrentUserStates(users);
    }

    private Map<Long, LoadUserStateResponse> makeCurrentUserStates(List<User> users) {
        Map<Long, LoadUserStateResponse> userStates = new HashMap<>();
        for (User user : users) {
            LoadUserStateResponse searchUserStateResponse = SearchUserStateResponseMapper.convert(user,
                    getConnectionStates(users).get(user.getUserId().getId()));
            userStates.put(user.getUserId().getId(), searchUserStateResponse);
        }
        return userStates;
    }

    private Map<Long, String> getConnectionStates(List<User> users) {
        List<Long> userIds = users
                .stream()
                .map(user -> user.getUserId().getId())
                .toList();
        LoadUserStatusInDmRoomRequest searchUserStatusInDmRoomRequest = new LoadUserStatusInDmRoomRequest(userIds);
        LoadDmUserStateFeignResponse searchDmUserStateFeignResponse = userStatusClient.userStates(
                searchUserStatusInDmRoomRequest);
        return searchDmUserStateFeignResponse.getConnectionStates();
    }
}
