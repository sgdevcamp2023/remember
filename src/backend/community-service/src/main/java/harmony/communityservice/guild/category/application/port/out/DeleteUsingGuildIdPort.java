package harmony.communityservice.guild.category.application.port.out;

import harmony.communityservice.guild.guild.domain.Guild.GuildId;

public interface DeleteUsingGuildIdPort {

    void deleteByGuildId(GuildId guildId);
}
