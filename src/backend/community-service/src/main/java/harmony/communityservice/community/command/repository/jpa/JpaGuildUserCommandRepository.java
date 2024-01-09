package harmony.communityservice.community.command.repository.jpa;

import harmony.communityservice.community.domain.GuildUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGuildUserCommandRepository extends JpaRepository<GuildUser, Long> {
}
