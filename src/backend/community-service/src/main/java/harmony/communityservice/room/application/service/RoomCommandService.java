package harmony.communityservice.room.application.service;

import harmony.communityservice.common.annotation.UseCase;
import harmony.communityservice.room.application.port.in.DeleteRoomCommand;
import harmony.communityservice.room.application.port.in.DeleteRoomUseCase;
import harmony.communityservice.room.application.port.in.RegisterRoomCommand;
import harmony.communityservice.room.application.port.in.RegisterRoomUseCase;
import harmony.communityservice.room.application.port.out.DeleteRoomPort;
import harmony.communityservice.room.application.port.out.RegisterRoomPort;
import harmony.communityservice.room.domain.Room;
import harmony.communityservice.user.domain.User.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
class RoomCommandService implements RegisterRoomUseCase, DeleteRoomUseCase {

    private final RegisterRoomPort registerRoomPort;
    private final DeleteRoomPort deleteRoomPort;

    @Override
    public void register(RegisterRoomCommand registerRoomCommand) {
        Room room = RoomMapper.convert(registerRoomCommand);
        registerRoomPort.register(room);
    }

    @Override
    public void delete(DeleteRoomCommand deleteRoomCommand) {
        UserId firstUserId = UserId.make(deleteRoomCommand.firstUser());
        UserId secondUserId = UserId.make(deleteRoomCommand.secondUser());
        deleteRoomPort.delete(firstUserId, secondUserId);
    }
}
