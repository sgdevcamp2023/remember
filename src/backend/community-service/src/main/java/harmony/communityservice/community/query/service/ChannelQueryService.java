package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.Channel;
import harmony.communityservice.community.query.dto.SearchChannelResponse;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ChannelQueryService {

    Channel searchByChannelId(long channelId);

    Map<Long, SearchChannelResponse> searchMapByGuildId(Long guildId, Long userId);
}
