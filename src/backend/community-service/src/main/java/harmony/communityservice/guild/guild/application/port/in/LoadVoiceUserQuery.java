package harmony.communityservice.guild.guild.application.port.in;

import harmony.communityservice.guild.guild.domain.GuildRead;

public interface LoadVoiceUserQuery {

    GuildRead loadByUserIdAndGuildId(LoadGuildReadCommand loadGuildReadCommand);
}
