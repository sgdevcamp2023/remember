package harmony.communityservice.guild.channel.repository.query;

import harmony.communityservice.guild.channel.domain.Channel;
import java.util.List;

public interface ChannelQueryRepository {

    List<Channel> findListByGuildId(Long guildId);
}
