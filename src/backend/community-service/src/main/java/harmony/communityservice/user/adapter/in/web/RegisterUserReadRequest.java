package harmony.communityservice.user.adapter.in.web;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(toBuilder = true)
public record RegisterUserReadRequest(
        @NotNull long userId,
        @NotNull long guildId
) {
}
