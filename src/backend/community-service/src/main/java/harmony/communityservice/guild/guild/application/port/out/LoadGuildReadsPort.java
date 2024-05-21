package harmony.communityservice.guild.guild.application.port.out;

import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;

public interface LoadGuildReadsPort {
    List<GuildRead> loadListByGuildId(GuildId guildId);

    List<GuildRead> loadListByUserId(UserId userId);
}
