package harmony.communityservice.community.query.repository.impl;

import harmony.communityservice.community.domain.Guild;
import harmony.communityservice.community.query.repository.GuildQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaGuildQueryRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GuildQueryRepositoryImpl implements GuildQueryRepository {

    private final JpaGuildQueryRepository jpaGuildQueryRepository;

    @Override
    public Optional<Guild> findById(Long guildId) {
        return jpaGuildQueryRepository.findById(guildId);
    }

    @Override
    public Optional<Guild> findByInvitationCode(String invitationCode) {
        return jpaGuildQueryRepository.findByInviteCode(invitationCode);
    }
}
