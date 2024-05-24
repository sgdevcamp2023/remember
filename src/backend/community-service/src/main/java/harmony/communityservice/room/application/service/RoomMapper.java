package harmony.communityservice.room.application.service;

import harmony.communityservice.domain.Threshold;
import harmony.communityservice.room.application.port.in.RegisterRoomCommand;
import harmony.communityservice.room.domain.Room;
import harmony.communityservice.room.domain.Room.RoomId;
import harmony.communityservice.room.domain.RoomUser;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;

class RoomMapper {

    public static Room convert(RegisterRoomCommand registerRoomCommand) {
        List<RoomUser> roomUsers = registerRoomCommand.members().stream()
                .map(userId -> RoomUser.make(UserId.make(userId)))
                .toList();
        return Room.builder()
                .roomId(RoomId.make(Threshold.MIN.getValue()))
                .name(registerRoomCommand.name())
                .profile(registerRoomCommand.profile())
                .roomUsers(roomUsers)
                .build();
    }
}
