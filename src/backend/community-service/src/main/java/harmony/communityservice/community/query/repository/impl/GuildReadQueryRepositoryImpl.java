package harmony.communityservice.community.query.repository.impl;

import harmony.communityservice.community.domain.GuildRead;
import harmony.communityservice.community.query.repository.GuildReadQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaGuildReadQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GuildReadQueryRepositoryImpl implements GuildReadQueryRepository {

    private final JpaGuildReadQueryRepository jpaGuildReadQueryRepository;

    @Override
    public List<GuildRead> findGuildsByUserId(Long userId) {
        return jpaGuildReadQueryRepository.findGuildReadsByUserId(userId);
    }
}
