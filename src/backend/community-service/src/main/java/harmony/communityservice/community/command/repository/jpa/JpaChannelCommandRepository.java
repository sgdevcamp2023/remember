package harmony.communityservice.community.command.repository.jpa;

import harmony.communityservice.community.domain.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChannelCommandRepository extends JpaRepository<Channel, Long> {
}
