package harmony.communityservice.guild.guild.application.port.in;

import java.util.Map;

public interface LoadUserGuildListQuery {
    Map<Long, ?> loadGuilds(Long userId);
}
