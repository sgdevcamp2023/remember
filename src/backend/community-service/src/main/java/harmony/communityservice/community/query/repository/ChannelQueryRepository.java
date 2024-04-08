package harmony.communityservice.community.query.repository;

import harmony.communityservice.community.domain.Channel;
import java.util.List;
import java.util.Optional;

public interface ChannelQueryRepository {

    Optional<Channel> findByChannelId(long channelId);

    List<Channel> findChannelsByGuildId(Long guildId);
}
