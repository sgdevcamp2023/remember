package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(toBuilder = true)
public record RegisterGuildReadRequest(
        @NotNull Long guildId,
        @NotNull Long userId,
        @NotBlank String name,
        @NotBlank String profile
) {
}
