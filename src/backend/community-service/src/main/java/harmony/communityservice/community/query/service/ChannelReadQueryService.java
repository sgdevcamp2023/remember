package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.ChannelRead;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ChannelReadQueryService {

    Map<Long, ChannelRead> findChannelsByGuildId(Long guildId, Long userId);
}
