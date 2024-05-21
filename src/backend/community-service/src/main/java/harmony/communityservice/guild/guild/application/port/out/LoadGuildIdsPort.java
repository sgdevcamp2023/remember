package harmony.communityservice.guild.guild.application.port.out;

import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;

public interface LoadGuildIdsPort {
    List<GuildId> loadList(UserId userId);
}
