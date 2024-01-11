package harmony.communityservice.community.query.repository;

import harmony.communityservice.community.domain.Guild;
import java.util.Optional;

public interface GuildQueryRepository {
    Optional<Guild> findById(Long guildId);

    Optional<Guild> findByInvitationCode(String invitationCode);

    boolean existsByGuildIdAndManagerId(Long guildId, Long managerId);
}
