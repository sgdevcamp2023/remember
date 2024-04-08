package harmony.communityservice.guild.guild.repository.command.jpa;

import harmony.communityservice.guild.domain.GuildRead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGuildReadCommandRepository extends JpaRepository<GuildRead, Long> {

    void deleteGuildReadsByGuildId(long guildId);

}
