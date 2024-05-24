package harmony.communityservice.room.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import harmony.communityservice.common.client.UserStatusClient;
import harmony.communityservice.common.dto.SearchDmUserStateFeignResponse;
import harmony.communityservice.common.dto.SearchUserStatusInDmRoomRequest;
import harmony.communityservice.room.application.port.in.SearchRoomsResponse;
import harmony.communityservice.room.application.port.in.SearchUserStateResponse;
import harmony.communityservice.room.application.port.out.LoadRoomIdsPort;
import harmony.communityservice.room.application.port.out.LoadRoomPort;
import harmony.communityservice.room.application.port.out.LoadRoomsPort;
import harmony.communityservice.room.domain.Room;
import harmony.communityservice.room.domain.Room.RoomId;
import harmony.communityservice.room.domain.RoomUser;
import harmony.communityservice.room.domain.RoomUser.RoomUserId;
import harmony.communityservice.user.application.port.in.LoadUserUseCase;
import harmony.communityservice.user.domain.User;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RoomQueryServiceTest {

    @Mock
    UserStatusClient userStatusClient;
    @Mock
    LoadRoomsPort loadRoomsPort;
    @Mock
    LoadRoomPort loadRoomPort;
    @Mock
    LoadUserUseCase loadUserUseCase;
    @Mock
    LoadRoomIdsPort loadRoomIdsPort;

    RoomQueryService roomQueryService;

    @BeforeEach
    void setting() {
        roomQueryService = new RoomQueryService(userStatusClient, loadRoomsPort, loadRoomPort, loadUserUseCase,
                loadRoomIdsPort);
    }


    @Test
    @DisplayName("룸 리스트 조회 테스트")
    void load_room_list() {
        assertNotNull(roomQueryService);

        Room firstRoom = Room.builder()
                .roomId(RoomId.make(1L))
                .name("first_room")
                .profile("http://first.com/room")
                .build();

        Room secondRoom = Room.builder()
                .roomId(RoomId.make(2L))
                .name("second_room")
                .profile("http://second.com/room")
                .build();

        given(loadRoomsPort.loadRooms(UserId.make(1L))).willReturn(List.of(firstRoom, secondRoom));

        SearchRoomsResponse searchRoomsResponse = roomQueryService.loadList(1L);

        assertEquals(searchRoomsResponse.getRooms(), List.of(firstRoom, secondRoom));

        then(loadRoomsPort).should(times(1)).loadRooms(UserId.make(1L));
    }

    @Test
    @DisplayName("roomId 리스트 조회 테스트")
    void load_room_ids() {
        assertNotNull(roomQueryService);

        List<Long> roomIds = List.of(1L, 2L, 3L, 4L, 5L);
        given(loadRoomIdsPort.loadRoomIds(UserId.make(1L))).willReturn(roomIds);

        List<Long> result = roomQueryService.loadRoomIds(1L);

        assertEquals(result, roomIds);
        then(loadRoomIdsPort).should(times(1)).loadRoomIds(UserId.make(1L));
    }

    @Test
    @DisplayName("dm 룸의 유저 상태 정보 조회 테스트")
    void load_room_user_states() {
        assertNotNull(roomQueryService);

        Room room = Room.builder()
                .roomId(RoomId.make(1L))
                .name("room")
                .profile("http://test.com/room")
                .roomUsers(List.of(RoomUser.make(RoomUserId.make(1L), UserId.make(1L)),
                        RoomUser.make(RoomUserId.make(1L), UserId.make(2L))))
                .build();
        User user = User.builder()
                .userId(1L)
                .nickname("0chord")
                .email("seaweed.0chord@gmail.com")
                .profile("https://storage.googleapis.com/sg-dev-remember-harmony/discord.png")
                .build();

        User secondUser = User.builder()
                .userId(2L)
                .nickname("0Chord")
                .email("seaweed.0Chord@gmail.com")
                .profile("https://storage.googleapis.com/sg-dev-remember-harmony/discord")
                .build();
        Map<Long, String> userStates = Map.of(1L, "ONLINE", 2L, "OFFLINE");
        given(loadRoomPort.loadByRoomId(RoomId.make(1L))).willReturn(room);
        given(loadUserUseCase.loadUser(1L)).willReturn(user);
        given(loadUserUseCase.loadUser(2L)).willReturn(secondUser);
        given(userStatusClient.getCommunityUsersState(
                new SearchUserStatusInDmRoomRequest(List.of(1L, 2L)))).willReturn(
                new SearchDmUserStateFeignResponse(userStates));

        Map<Long, SearchUserStateResponse> result = roomQueryService.loadUserStates(1L);



        assertEquals(result.get(1L).getUserId(), 1L);
        assertEquals(result.get(2L).getUserId(),2L);
        assertEquals(result.get(1L).getUserName(), "0chord");
        assertEquals(result.get(2L).getUserName(),"0Chord");
        assertEquals(result.get(1L).getProfile(), "https://storage.googleapis.com/sg-dev-remember-harmony/discord.png");
        assertEquals(result.get(2L).getProfile(),"https://storage.googleapis.com/sg-dev-remember-harmony/discord");
        then(loadRoomPort).should(times(1)).loadByRoomId(RoomId.make(1L));
    }

}