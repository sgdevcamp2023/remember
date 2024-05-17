package harmony.communityservice.guild.guild.application.port.out;

import harmony.communityservice.guild.guild.domain.GuildRead;

public interface RegisterGuildReadPort {

    void register(GuildRead guildRead);
}
