package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.RegisterRoomRequest;
import harmony.communityservice.community.domain.Room;

public class ToRoomMapper {

    public static Room convert(RegisterRoomRequest registerRoomRequest) {
        return Room.builder()
                .name(registerRoomRequest.name())
                .profile(registerRoomRequest.profile())
                .build();
    }
}
