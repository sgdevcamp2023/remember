package harmony.communityservice.community.query.repository;

import harmony.communityservice.community.domain.GuildRead;
import java.util.List;

public interface GuildReadQueryRepository {
    List<GuildRead> findGuildsByUserId(Long userId);
}
