package harmony.communityservice.community.query.repository;

import harmony.communityservice.community.domain.ChannelRead;
import java.util.List;

public interface ChannelReadQueryRepository {
    List<ChannelRead> findChannelReadsByGuildId(Long guildId);
}
