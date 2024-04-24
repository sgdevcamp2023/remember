package harmony.communityservice.guild.channel.repository.command;

import harmony.communityservice.guild.channel.domain.Channel;

public interface ChannelCommandRepository {
    void save(Channel channel);

    void deleteById(Long channelId);

    void deleteByGuildId(Long guildId);
}
