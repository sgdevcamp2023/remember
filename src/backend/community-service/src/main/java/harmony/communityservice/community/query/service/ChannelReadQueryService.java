package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.ChannelRead;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ChannelReadQueryService {

    List<ChannelRead> findChannelsByGuildId(Long guildId, Long userId);
}
