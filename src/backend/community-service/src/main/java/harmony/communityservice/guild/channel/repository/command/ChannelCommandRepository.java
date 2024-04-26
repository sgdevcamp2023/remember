package harmony.communityservice.guild.channel.repository.command;

import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.channel.domain.ChannelId;
import harmony.communityservice.guild.channel.domain.ChannelType;
import harmony.communityservice.guild.guild.domain.GuildId;
import java.util.List;

public interface ChannelCommandRepository {
    void save(Channel channel);

    void deleteById(ChannelId channelId);

    void deleteByGuildId(GuildId guildId);

    List<ChannelId> findIdsByGuildIdAndType(GuildId guildId, ChannelType type);
}
