package harmony.communityservice.community.query.repository.impl;

import harmony.communityservice.community.domain.UserRead;
import harmony.communityservice.community.query.repository.UserReadQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaUserReadQueryRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserReadQueryRepositoryImpl implements UserReadQueryRepository {

    private final JpaUserReadQueryRepository jpaUserReadQueryRepository;

    @Override
    public boolean existByUserIdAndGuildId(Long userid, Long guildId) {
        return jpaUserReadQueryRepository.existsByGuildIdAndUserId(guildId, userid);
    }

    @Override
    public Optional<UserRead> findByUserIdAndGuildId(Long userId, Long guildId) {
        return jpaUserReadQueryRepository.findByUserIdAndGuildId(userId, guildId);
    }

    @Override
    public List<UserRead> findUserReadsByUserId(Long userId) {
        return jpaUserReadQueryRepository.findUserReadByUserId(userId);
    }

    @Override
    public List<UserRead> findUserReadsByGuildId(Long guildId) {
        return jpaUserReadQueryRepository.findUserReadsByGuildId(guildId);
    }
}
