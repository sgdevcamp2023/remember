package harmony.communityservice.guild.guild.application.port.out;

import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.user.domain.User.UserId;

public interface DeleteGuildPort {

    void delete(GuildId guildId, UserId userId);
}
