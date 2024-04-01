package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.Channel;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ChannelQueryService {

    Channel searchByChannelId(long channelId);
}
