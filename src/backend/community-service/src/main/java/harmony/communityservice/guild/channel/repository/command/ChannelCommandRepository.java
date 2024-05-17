package harmony.communityservice.guild.channel.repository.command;

import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.channel.domain.ChannelId;
import harmony.communityservice.guild.channel.domain.ChannelType;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO;
import java.util.List;

public interface ChannelCommandRepository {
    void save(Channel channel);

    void deleteById(ChannelId channelId);

    void deleteByGuildId(GuildIdJpaVO guildId);

    List<ChannelId> findIdsByGuildIdAndType(GuildIdJpaVO guildId, ChannelType type);
}
