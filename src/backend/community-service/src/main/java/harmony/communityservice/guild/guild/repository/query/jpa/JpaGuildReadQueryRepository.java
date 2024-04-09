package harmony.communityservice.guild.guild.repository.query.jpa;

import harmony.communityservice.guild.domain.GuildRead;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGuildReadQueryRepository extends JpaRepository<GuildRead, Long> {
    List<GuildRead> findGuildReadsByUserId(Long userId);
}
