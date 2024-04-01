package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.GuildRead;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface GuildReadQueryService {

    Map<Long, GuildRead> searchMapByUserId(long userId);
}
