package harmony.communityservice.community.command.repository.jpa;

import harmony.communityservice.community.domain.ChannelRead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChannelReadCommandRepository extends JpaRepository<ChannelRead, Long> {
}
