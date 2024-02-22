package harmony.communityservice.community.query.repository.jpa;

import harmony.communityservice.community.domain.ChannelRead;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChannelReadQueryRepository extends JpaRepository<ChannelRead, Long> {
    List<ChannelRead> findChannelReadsByGuildId(Long guildId);
}
