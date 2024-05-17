package harmony.communityservice.guild.guild.application.port.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(toBuilder = true)
public record RegisterGuildReadCommand(
        Long guildId,
        Long userId,
        String name,
        String profile
) {
}
