package harmony.communityservice.room.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record RegisterRoomRequest(
        @NotBlank String name,
        List<Long> members,
        @NotBlank String profile,
        @NotNull Long userId
) {
}