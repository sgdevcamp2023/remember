package harmony.communityservice.guild.channel.repository.query.impl;

import harmony.communityservice.guild.channel.repository.query.ChannelQueryRepository;
import harmony.communityservice.guild.channel.repository.query.jpa.JpaChannelQueryRepository;
import harmony.communityservice.guild.domain.Channel;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChannelQueryRepositoryImpl implements ChannelQueryRepository {

    private final JpaChannelQueryRepository jpaChannelQueryRepository;

    @Override
    public Optional<Channel> findByChannelId(long channelId) {
        return jpaChannelQueryRepository.findById(channelId);
    }

    @Override
    public List<Channel> findChannelsByGuildId(Long guildId) {
        return jpaChannelQueryRepository.findChannelsByGuildId(guildId);
    }
}
