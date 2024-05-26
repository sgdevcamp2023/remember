package harmony.communityservice.room.application.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import harmony.communityservice.domain.Threshold;
import harmony.communityservice.room.application.port.in.DeleteRoomCommand;
import harmony.communityservice.room.application.port.in.RegisterRoomCommand;
import harmony.communityservice.room.application.port.out.DeleteRoomPort;
import harmony.communityservice.room.application.port.out.RegisterRoomPort;
import harmony.communityservice.room.domain.Room;
import harmony.communityservice.room.domain.Room.RoomId;
import harmony.communityservice.room.domain.RoomUser;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RoomCommandServiceTest {

    @Mock
    RegisterRoomPort registerRoomPort;

    @Mock
    DeleteRoomPort deleteRoomPort;

    RoomCommandService roomCommandService;

    @BeforeEach
    void setting() {
        roomCommandService = new RoomCommandService(registerRoomPort, deleteRoomPort);
    }

    @Test
    @DisplayName("Dm 룸 등록 테스트")
    void register_room() {
        assertNotNull(roomCommandService);

        RoomUser first = RoomUser.make(UserId.make(1L));
        RoomUser second = RoomUser.make(UserId.make(2L));
        Room room = Room.builder()
                .roomId(RoomId.make(Threshold.MIN.getValue()))
                .name("testRoom")
                .profile("example")
                .roomUsers(List.of(first, second))
                .build();

        willDoNothing().given(registerRoomPort).register(room);

        RegisterRoomCommand registerRoomCommand = RegisterRoomCommand.builder()
                .userId(1L)
                .members(List.of(1L, 2L))
                .profile("example")
                .name("testRoom")
                .build();
        roomCommandService.register(registerRoomCommand);

        then(registerRoomPort).should(times(1)).register(room);
    }

    @Test
    @DisplayName("Dm 룸 삭제 테스트")
    void delete_room() {
        assertNotNull(roomCommandService);

        UserId first = UserId.make(1L);
        UserId second = UserId.make(2L);

        willDoNothing().given(deleteRoomPort).delete(first, second);

        DeleteRoomCommand deleteRoomCommand = new DeleteRoomCommand(1L, 1L, 2L);
        roomCommandService.delete(deleteRoomCommand);

        then(deleteRoomPort).should(times(1)).delete(first, second);
    }
}