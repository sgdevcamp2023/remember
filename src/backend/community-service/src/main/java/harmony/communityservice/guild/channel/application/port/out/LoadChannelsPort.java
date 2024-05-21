package harmony.communityservice.guild.channel.application.port.out;

import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import java.util.List;

public interface LoadChannelsPort {

    List<Channel> loadChannels(GuildId guildId);
}
