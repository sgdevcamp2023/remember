package harmony.communityservice.guild.guild.application.port.in;

import harmony.communityservice.guild.guild.domain.GuildRead;

public interface LoadGuildReadUseCase {

    GuildRead searchByUserIdAndGuildId(Long userId, Long guildId);
}
