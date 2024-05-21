package harmony.communityservice.guild.channel.application.port.out;

import harmony.communityservice.guild.guild.domain.Guild.GuildId;

public interface DeleteGuildChannelsPort {

    void deleteByGuildId(GuildId guildId);
}
