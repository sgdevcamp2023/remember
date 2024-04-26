package harmony.communityservice.guild.guild.repository.query.jpa;

import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.guild.guild.domain.GuildReadId;
import harmony.communityservice.user.domain.UserId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGuildReadQueryRepository extends JpaRepository<GuildRead, GuildReadId> {
    List<GuildRead> findGuildReadsByUserId(UserId userId);
}
