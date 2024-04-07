package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.RegisterRoomRequest;
import harmony.communityservice.community.domain.Room;
import java.util.HashSet;
import java.util.Set;

public class ToRoomMapper {

    public static Room convert(RegisterRoomRequest registerRoomRequest) {
        Set<Long> userIds = new HashSet<>(registerRoomRequest.members());
        return Room.builder()
                .name(registerRoomRequest.name())
                .profile(registerRoomRequest.profile())
                .userIds(userIds)
                .build();
    }
}
