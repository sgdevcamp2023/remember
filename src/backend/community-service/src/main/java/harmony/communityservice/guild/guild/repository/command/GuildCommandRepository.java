package harmony.communityservice.guild.guild.repository.command;

import harmony.communityservice.guild.guild.domain.Guild;
import java.util.Optional;

public interface GuildCommandRepository {
    void save(Guild guild);

    void deleteById(Long guildId);

    Optional<Guild> findById(Long guildId);

    Optional<Guild> findByInvitationCode(String invitationCode);
}
