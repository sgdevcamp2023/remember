package harmony.communityservice.room.mapper;

import harmony.communityservice.room.domain.Room;
import harmony.communityservice.room.dto.SearchRoomResponse;

public class ToSearchRoomResponseMapper {

    public static SearchRoomResponse convert(Room room) {
        return SearchRoomResponse.builder()
                .profile(room.getRoomInfo().getProfile())
                .name(room.getRoomInfo().getName())
                .roomId(room.getRoomId().getId())
                .build();
    }
}
