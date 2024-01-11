package harmony.communityservice.community.command.repository.jpa;

import harmony.communityservice.community.domain.GuildRead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGuildReadCommandRepository extends JpaRepository<GuildRead, Long> {


}
