package harmony.communityservice.guild.channel.repository.query;

import harmony.communityservice.guild.domain.Channel;
import java.util.List;

public interface ChannelQueryRepository {

    List<Channel> findListByGuildId(Long guildId);
}
