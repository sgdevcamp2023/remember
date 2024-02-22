package harmony.communityservice.community.query.repository.jpa;

import harmony.communityservice.community.domain.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChannelQueryRepository extends JpaRepository<Channel, Long> {
}
