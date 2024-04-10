package harmony.communityservice.guild.guild.repository.command;

import harmony.communityservice.guild.domain.Guild;
import java.util.Optional;

public interface GuildCommandRepository {
    void save(Guild guild);

    void delete(Long guildId);

    Optional<Guild> findById(Long guildId);

    Optional<Guild> findByInvitationCode(String invitationCode);
}
