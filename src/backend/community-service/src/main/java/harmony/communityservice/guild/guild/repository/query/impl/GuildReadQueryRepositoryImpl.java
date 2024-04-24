package harmony.communityservice.guild.guild.repository.query.impl;

import harmony.communityservice.guild.guild.repository.query.GuildReadQueryRepository;
import harmony.communityservice.guild.guild.repository.query.jpa.JpaGuildReadQueryRepository;
import harmony.communityservice.guild.guild.domain.GuildRead;
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
