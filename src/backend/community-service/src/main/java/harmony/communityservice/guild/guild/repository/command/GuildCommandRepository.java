package harmony.communityservice.guild.guild.repository.command;

import harmony.communityservice.guild.domain.Guild;

public interface GuildCommandRepository {
    void save(Guild guild);

    void delete(Long guildId);
}
