package harmony.communityservice.community.query.repository.impl;

import harmony.communityservice.community.domain.Channel;
import harmony.communityservice.community.query.repository.ChannelQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaChannelQueryRepository;
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
