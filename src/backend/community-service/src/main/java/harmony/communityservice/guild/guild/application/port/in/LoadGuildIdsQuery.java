package harmony.communityservice.guild.guild.application.port.in;

import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;

public interface LoadGuildIdsQuery {

    List<GuildId> loadGuildIdsByUserId(UserId userId);
}
