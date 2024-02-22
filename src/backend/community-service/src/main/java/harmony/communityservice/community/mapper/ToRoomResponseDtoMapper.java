package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Room;
import harmony.communityservice.community.query.dto.RoomResponseDto;

public class ToRoomResponseDtoMapper {

    public static RoomResponseDto convert(Room room) {
        return RoomResponseDto.builder()
                .profile(room.getProfile())
                .name(room.getName())
                .roomId(room.getRoomId())
                .build();
    }
}
