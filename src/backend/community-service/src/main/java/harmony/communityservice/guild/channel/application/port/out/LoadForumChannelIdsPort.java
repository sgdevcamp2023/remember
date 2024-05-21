package harmony.communityservice.guild.channel.application.port.out;

import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import harmony.communityservice.guild.channel.domain.ChannelType;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import java.util.List;

public interface LoadForumChannelIdsPort {
    List<ChannelId> loadForumChannelIds(GuildId guildId, ChannelType channelType);
}
