package harmony.communityservice.guild.guild.repository.query.impl;

import harmony.communityservice.guild.guild.domain.Guild;
import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.guild.guild.repository.query.GuildQueryRepository;
import harmony.communityservice.guild.guild.repository.query.jpa.JpaGuildQueryRepository;
import harmony.communityservice.user.adapter.out.persistence.UserId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;

@RequiredArgsConstructor
public class GuildQueryRepositoryImpl implements GuildQueryRepository {

    private final JpaGuildQueryRepository jpaGuildQueryRepository;

    @Override
    @Cacheable(value = "guild", key = "#guildId")
    public Optional<Guild> findById(GuildId guildId) {
        return jpaGuildQueryRepository.findById(guildId);
    }

    @Override
    public Optional<Guild> findByInvitationCode(String invitationCode) {
        return jpaGuildQueryRepository.findByInviteCode(invitationCode);
    }

    @Override
    public boolean existsByGuildIdAndManagerId(GuildId guildId, UserId managerId) {
        return jpaGuildQueryRepository.existsByGuildIdAndManagerId(guildId, managerId);
    }
}
