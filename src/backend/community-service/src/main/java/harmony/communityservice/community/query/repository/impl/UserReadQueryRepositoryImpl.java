package harmony.communityservice.community.query.repository.impl;

import harmony.communityservice.community.query.repository.UserReadQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaUserReadQueryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserReadQueryRepositoryImpl implements UserReadQueryRepository {

    private final JpaUserReadQueryRepository jpaUserReadQueryRepository;

    @Override
    public boolean existByUserIdAndGuildId(Long userid, Long guildId) {
        return jpaUserReadQueryRepository.existsByGuildIdAndUserId(guildId, userid);
    }
}
