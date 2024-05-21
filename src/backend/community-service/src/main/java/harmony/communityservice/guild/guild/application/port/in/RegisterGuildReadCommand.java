package harmony.communityservice.guild.guild.application.port.in;

import lombok.Builder;

@Builder(toBuilder = true)
public record RegisterGuildReadCommand(
        Long guildId,
        Long userId,
        String name,
        String profile
) {
}
