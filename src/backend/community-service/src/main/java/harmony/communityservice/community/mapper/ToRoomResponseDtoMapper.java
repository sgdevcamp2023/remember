package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Room;
import harmony.communityservice.community.query.dto.SearchRoomResponse;

public class ToRoomResponseDtoMapper {

    public static SearchRoomResponse convert(Room room) {
        return SearchRoomResponse.builder()
                .profile(room.getRoomInfo().getProfile())
                .name(room.getRoomInfo().getName())
                .roomId(room.getRoomId())
                .build();
    }
}
