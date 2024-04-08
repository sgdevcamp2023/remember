package harmony.communityservice.guild.guild.repository.command.jpa;

import harmony.communityservice.guild.domain.Guild;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGuildCommandRepository extends JpaRepository<Guild, Long> {
}
