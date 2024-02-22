package harmony.communityservice.community.query.repository;

import harmony.communityservice.community.domain.Channel;
import java.util.Optional;

public interface ChannelQueryRepository {

    Optional<Channel> findByChannelId(long channelId);
}
