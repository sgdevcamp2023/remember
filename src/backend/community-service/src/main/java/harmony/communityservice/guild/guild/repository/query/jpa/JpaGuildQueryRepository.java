package harmony.communityservice.guild.guild.repository.query.jpa;

import harmony.communityservice.guild.domain.Guild;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGuildQueryRepository extends JpaRepository<Guild,Long> {
    Optional<Guild> findByInviteCode(String code);

    boolean existsByGuildIdAndManagerId(Long guildId, Long managerId);
}
