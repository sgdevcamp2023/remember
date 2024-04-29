package harmony.communityservice.guild.guild.repository.command.jpa;

import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.guild.guild.domain.GuildReadId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaGuildReadCommandRepository extends JpaRepository<GuildRead, GuildReadId> {

    @Modifying
    @Query("delete from GuildRead gr where gr.guildId = :guildId")
    void deleteGuildReadsByGuildId(GuildId guildId);


}
