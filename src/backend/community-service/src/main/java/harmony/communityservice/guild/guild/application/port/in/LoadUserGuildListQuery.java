package harmony.communityservice.guild.guild.application.port.in;

import harmony.communityservice.guild.guild.adapter.in.web.SearchGuildReadResponse;
import java.util.Map;

public interface LoadUserGuildListQuery {
    Map<Long, SearchGuildReadResponse> loadGuilds(Long userId);
}
