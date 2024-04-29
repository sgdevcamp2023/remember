package harmony.communityservice.guild.guild.repository.query.impl;

import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.guild.guild.repository.query.GuildReadQueryRepository;
import harmony.communityservice.guild.guild.repository.query.jpa.JpaGuildReadQueryRepository;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.user.domain.UserId;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GuildReadQueryRepositoryImpl implements GuildReadQueryRepository {

    private final JpaGuildReadQueryRepository jpaGuildReadQueryRepository;

    @Override
    public List<GuildRead> findGuildsByUserId(UserId userId) {
        return jpaGuildReadQueryRepository.findGuildReadsByUserId(userId);
    }

    @Override
    public List<GuildId> findGuildIdsByUserId(UserId userId) {
        return jpaGuildReadQueryRepository.findGuildIdsByUserId(userId);
    }
}
