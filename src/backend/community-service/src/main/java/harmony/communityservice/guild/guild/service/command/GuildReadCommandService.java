package harmony.communityservice.guild.guild.service.command;

import harmony.communityservice.guild.guild.dto.RegisterGuildReadRequest;
import harmony.communityservice.guild.guild.domain.GuildRead;

public interface GuildReadCommandService {
    void register(RegisterGuildReadRequest registerGuildReadRequest);

    void delete(long guildId);
}
