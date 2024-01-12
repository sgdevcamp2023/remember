package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.ChannelRead;
import java.util.List;

public interface ChannelReadQueryService {

    List<ChannelRead> findChannelsByGuildId(Long guildId, Long userId);
}
