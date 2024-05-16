package harmony.communityservice.guild.guild.repository.query.jpa;

import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.guild.guild.domain.GuildReadId;
import harmony.communityservice.user.adapter.out.persistence.UserId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaGuildReadQueryRepository extends JpaRepository<GuildRead, GuildReadId> {

    @Query("select gr from GuildRead gr where gr.userId = :userId")
    List<GuildRead> findGuildReadsByUserId(UserId userId);

    @Query("select gr.guildId from GuildRead gr where gr.userId = :userId")
    List<GuildId> findGuildIdsByUserId(UserId userId);
}
