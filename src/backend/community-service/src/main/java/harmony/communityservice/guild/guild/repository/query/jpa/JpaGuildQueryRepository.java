package harmony.communityservice.guild.guild.repository.query.jpa;

import harmony.communityservice.guild.guild.domain.Guild;
import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.user.domain.UserId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGuildQueryRepository extends JpaRepository<Guild, GuildId> {
    Optional<Guild> findByInviteCode(String code);

    boolean existsByGuildIdAndManagerId(GuildId guildId, UserId managerId);
}
