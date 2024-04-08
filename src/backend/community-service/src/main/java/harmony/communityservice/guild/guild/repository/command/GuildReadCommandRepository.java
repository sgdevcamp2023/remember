package harmony.communityservice.guild.guild.repository.command;

import harmony.communityservice.guild.domain.GuildRead;

public interface GuildReadCommandRepository {

    void save(GuildRead guildRead);

    void delete(long guildId);
}
