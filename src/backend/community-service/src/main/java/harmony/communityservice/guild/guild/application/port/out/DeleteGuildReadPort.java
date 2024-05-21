package harmony.communityservice.guild.guild.application.port.out;

import harmony.communityservice.guild.guild.domain.Guild.GuildId;

public interface DeleteGuildReadPort {

    void delete(GuildId guildId);
}
