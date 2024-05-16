package harmony.communityservice.guild.guild.repository.query;

import harmony.communityservice.guild.guild.domain.Guild;
import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.user.adapter.out.persistence.UserId;
import java.util.Optional;

public interface GuildQueryRepository {
    Optional<Guild> findById(GuildId guildId);

    Optional<Guild> findByInvitationCode(String invitationCode);

    boolean existsByGuildIdAndManagerId(GuildId guildId, UserId managerId);
}
