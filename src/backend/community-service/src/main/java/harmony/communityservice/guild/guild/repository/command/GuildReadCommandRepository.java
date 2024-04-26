package harmony.communityservice.guild.guild.repository.command;

import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead;

public interface GuildReadCommandRepository {

    void save(GuildRead guildRead);

    void delete(GuildId guildId);
}
