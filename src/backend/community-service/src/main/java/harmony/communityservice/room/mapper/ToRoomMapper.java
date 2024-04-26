package harmony.communityservice.room.mapper;

import harmony.communityservice.room.domain.Room;
import harmony.communityservice.room.domain.RoomUser;
import harmony.communityservice.room.dto.RegisterRoomRequest;
import harmony.communityservice.user.domain.UserId;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ToRoomMapper {

    public static Room convert(RegisterRoomRequest registerRoomRequest) {
        List<RoomUser> roomUsers = registerRoomRequest.members()
                .stream()
                .map(UserId::make)
                .map(RoomUser::make)
                .collect(Collectors.toList());
        return Room.builder()
                .name(registerRoomRequest.name())
                .profile(registerRoomRequest.profile())
                .roomUsers(roomUsers)
                .build();
    }
}
