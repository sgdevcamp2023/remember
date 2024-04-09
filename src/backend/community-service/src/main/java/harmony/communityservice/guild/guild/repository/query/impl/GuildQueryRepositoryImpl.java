package harmony.communityservice.guild.guild.repository.query.impl;

import harmony.communityservice.guild.guild.repository.query.GuildQueryRepository;
import harmony.communityservice.guild.guild.repository.query.jpa.JpaGuildQueryRepository;
import harmony.communityservice.guild.domain.Guild;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;

@RequiredArgsConstructor
public class GuildQueryRepositoryImpl implements GuildQueryRepository {

    private final JpaGuildQueryRepository jpaGuildQueryRepository;

    @Override
    @Cacheable(value = "guild", key = "#guildId")
    public Optional<Guild> findById(Long guildId) {
        return jpaGuildQueryRepository.findById(guildId);
    }

    @Override
    public Optional<Guild> findByInvitationCode(String invitationCode) {
        return jpaGuildQueryRepository.findByInviteCode(invitationCode);
    }

    @Override
    public boolean existsByGuildIdAndManagerId(Long guildId, Long managerId) {
        return jpaGuildQueryRepository.existsByGuildIdAndManagerId(guildId, managerId);
    }
}
