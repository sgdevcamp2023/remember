package harmony.communityservice.guild.guild.application.port.in;

import harmony.communityservice.guild.guild.domain.GuildRead;
import java.util.List;

public interface LoadGuildReadsQuery {

    List<GuildRead> loadList(Long guildId);
}
