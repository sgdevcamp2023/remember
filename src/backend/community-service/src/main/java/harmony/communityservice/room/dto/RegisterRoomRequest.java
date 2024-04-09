package harmony.communityservice.room.dto;

import java.util.List;

public record RegisterRoomRequest(
        String name,
        List<Long> members,
        String profile
) {
}