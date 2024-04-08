package harmony.communityservice.room.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record SearchRoomResponse(
        Long roomId,
        String name,
        String profile
) {
}

