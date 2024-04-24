package harmony.communityservice.guild.guild.repository.query;

import harmony.communityservice.guild.guild.domain.Guild;
import java.util.Optional;

public interface GuildQueryRepository {
    Optional<Guild> findById(Long guildId);

    Optional<Guild> findByInvitationCode(String invitationCode);

    boolean existsByGuildIdAndManagerId(Long guildId, Long managerId);
}
