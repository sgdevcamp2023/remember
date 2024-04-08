package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
public record RegisterUserReadRequest(
        @NotNull long userId,
        @NotNull long guildId
) {
}
