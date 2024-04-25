package harmony.communityservice.guild.channel.repository.command;

import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.channel.domain.ChannelType;
import java.util.List;

public interface ChannelCommandRepository {
    void save(Channel channel);

    void deleteById(Long channelId);

    void deleteByGuildId(Long guildId);

    List<Long> findIdsByGuildIdAndType(Long guildId, ChannelType type);
}
