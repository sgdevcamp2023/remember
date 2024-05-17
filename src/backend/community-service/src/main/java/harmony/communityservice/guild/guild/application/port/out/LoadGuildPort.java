package harmony.communityservice.guild.guild.application.port.out;

import harmony.communityservice.guild.guild.domain.Guild;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;

public interface LoadGuildPort {

    Guild loadById(GuildId guildId);
}
