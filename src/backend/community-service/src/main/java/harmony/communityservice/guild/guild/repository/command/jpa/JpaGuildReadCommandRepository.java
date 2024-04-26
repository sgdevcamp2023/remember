package harmony.communityservice.guild.guild.repository.command.jpa;

import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.guild.guild.domain.GuildReadId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGuildReadCommandRepository extends JpaRepository<GuildRead, GuildReadId> {

    void deleteGuildReadsByGuildId(GuildId guildId);

}
