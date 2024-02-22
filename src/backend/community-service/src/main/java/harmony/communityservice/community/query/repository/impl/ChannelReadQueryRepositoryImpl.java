package harmony.communityservice.community.query.repository.impl;

import harmony.communityservice.community.domain.ChannelRead;
import harmony.communityservice.community.query.repository.ChannelReadQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaChannelReadQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChannelReadQueryRepositoryImpl implements ChannelReadQueryRepository {

    private final JpaChannelReadQueryRepository jpaChannelReadQueryRepository;

    @Override
    public List<ChannelRead> findChannelReadsByGuildId(Long guildId) {
        return jpaChannelReadQueryRepository.findChannelReadsByGuildId(guildId);
    }
}
