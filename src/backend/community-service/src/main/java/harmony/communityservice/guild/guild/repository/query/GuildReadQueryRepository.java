package harmony.communityservice.guild.guild.repository.query;

import harmony.communityservice.guild.domain.GuildRead;
import java.util.List;

public interface GuildReadQueryRepository {
    List<GuildRead> findGuildsByUserId(Long userId);
}
