package harmony.communityservice.guild.guild.service.query;

import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.guild.guild.dto.SearchGuildReadResponse;
import harmony.communityservice.user.domain.UserId;
import java.util.List;
import java.util.Map;

public interface GuildReadQueryService {

    Map<Long, SearchGuildReadResponse> searchMapByUserId(long userId);

    List<GuildId> searchGuildIdsByUserId(UserId userId);
}
