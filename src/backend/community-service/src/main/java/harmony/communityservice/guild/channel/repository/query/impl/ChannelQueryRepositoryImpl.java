package harmony.communityservice.guild.channel.repository.query.impl;

import harmony.communityservice.guild.channel.repository.query.ChannelQueryRepository;
import harmony.communityservice.guild.channel.repository.query.jpa.JpaChannelQueryRepository;
import harmony.communityservice.guild.channel.domain.Channel;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChannelQueryRepositoryImpl implements ChannelQueryRepository {

    private final JpaChannelQueryRepository jpaChannelQueryRepository;

    @Override
    public List<Channel> findListByGuildId(Long guildId) {
        return jpaChannelQueryRepository.findChannelsByGuildId(guildId);
    }
}
