package harmony.communityservice.community.query.repository.jpa;

import harmony.communityservice.community.domain.GuildRead;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGuildReadQueryRepository extends JpaRepository<GuildRead, Long> {
    List<GuildRead> findGuildReadsByUserId(Long userId);
}
