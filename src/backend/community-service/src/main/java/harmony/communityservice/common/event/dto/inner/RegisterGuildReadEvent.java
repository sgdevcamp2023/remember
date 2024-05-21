package harmony.communityservice.common.event.dto.inner;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(toBuilder = true)
public record RegisterGuildReadEvent(@NotNull Long guildId,
                                     @NotNull Long userId,
                                     @NotBlank String name,
                                     @NotBlank String profile) {
}
