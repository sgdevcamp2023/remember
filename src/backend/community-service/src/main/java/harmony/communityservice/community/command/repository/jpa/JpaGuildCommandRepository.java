package harmony.communityservice.community.command.repository.jpa;

import harmony.communityservice.community.domain.Guild;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGuildCommandRepository extends JpaRepository<Guild, Long> {
}
