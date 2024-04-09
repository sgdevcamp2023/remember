package harmony.communityservice.guild.guild.service.command;

import harmony.communityservice.guild.guild.dto.RegisterGuildReadRequest;
import harmony.communityservice.guild.domain.GuildRead;

public interface GuildReadCommandService {
    GuildRead register(RegisterGuildReadRequest registerGuildReadRequest);

    void delete(long guildId);
}
