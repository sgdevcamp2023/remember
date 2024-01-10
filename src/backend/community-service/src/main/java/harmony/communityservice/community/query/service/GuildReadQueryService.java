package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.GuildRead;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface GuildReadQueryService {

    List<GuildRead> findGuildReadsByUserId(long userId);
}
