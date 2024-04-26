package harmony.communityservice.guild.guild.repository.command;

import harmony.communityservice.guild.guild.domain.Guild;
import harmony.communityservice.guild.guild.domain.GuildId;
import java.util.Optional;

public interface GuildCommandRepository {
    void save(Guild guild);

    void deleteById(GuildId guildId);

    Optional<Guild> findById(GuildId guildId);

    Optional<Guild> findByInvitationCode(String invitationCode);
}
