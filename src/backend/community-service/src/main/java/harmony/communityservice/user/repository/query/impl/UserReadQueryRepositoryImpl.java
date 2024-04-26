package harmony.communityservice.user.repository.query.impl;

import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.user.domain.UserId;
import harmony.communityservice.user.domain.UserRead;
import harmony.communityservice.user.repository.query.UserReadQueryRepository;
import harmony.communityservice.user.repository.query.jpa.JpaUserReadQueryRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserReadQueryRepositoryImpl implements UserReadQueryRepository {

    private final JpaUserReadQueryRepository jpaUserReadQueryRepository;

    @Override
    public boolean existByUserIdAndGuildId(UserId userid, GuildId guildId) {
        return jpaUserReadQueryRepository.existsByGuildIdAndUserId(guildId, userid);
    }

    @Override
    public Optional<UserRead> findByUserIdAndGuildId(UserId userId, GuildId guildId) {
        return jpaUserReadQueryRepository.findByUserIdAndGuildId(userId, guildId);
    }

    @Override
    public List<UserRead> findUserReadsByUserId(UserId userId) {
        return jpaUserReadQueryRepository.findUserReadByUserId(userId);
    }

    @Override
    public List<UserRead> findUserReadsByGuildId(GuildId guildId) {
        return jpaUserReadQueryRepository.findUserReadsByGuildId(guildId);
    }
}
