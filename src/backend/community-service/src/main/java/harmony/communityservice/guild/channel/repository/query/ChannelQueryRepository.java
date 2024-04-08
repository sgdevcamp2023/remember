package harmony.communityservice.guild.channel.repository.query;

import harmony.communityservice.guild.domain.Channel;
import java.util.List;
import java.util.Optional;

public interface ChannelQueryRepository {

    Optional<Channel> findByChannelId(long channelId);

    List<Channel> findChannelsByGuildId(Long guildId);
}
