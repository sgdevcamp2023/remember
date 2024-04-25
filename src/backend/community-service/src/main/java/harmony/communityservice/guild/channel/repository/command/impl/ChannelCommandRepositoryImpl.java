package harmony.communityservice.guild.channel.repository.command.impl;

import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.channel.domain.ChannelType;
import harmony.communityservice.guild.channel.repository.command.ChannelCommandRepository;
import harmony.communityservice.guild.channel.repository.command.jpa.JpaChannelCommandRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChannelCommandRepositoryImpl implements ChannelCommandRepository {

    private final JpaChannelCommandRepository jpaChannelCommandRepository;

    @Override
    public void save(Channel channel) {
        jpaChannelCommandRepository.save(channel);
    }

    @Override
    public void deleteById(Long channelId) {
        jpaChannelCommandRepository.deleteById(channelId);
    }

    @Override
    public void deleteByGuildId(Long guildId) {
        jpaChannelCommandRepository.deleteChannelsByGuildId(guildId);
    }

    @Override
    public List<Long> findIdsByGuildIdAndType(Long guildId, ChannelType type) {
        return jpaChannelCommandRepository.findChannelIdsByGuildIdAndType(guildId, type);
    }
}
