package harmony.communityservice.guild.guild.service.query;

import harmony.communityservice.guild.guild.domain.GuildRead;
import java.util.Map;

public interface GuildReadQueryService {

    Map<Long, GuildRead> searchMapByUserId(long userId);
}
