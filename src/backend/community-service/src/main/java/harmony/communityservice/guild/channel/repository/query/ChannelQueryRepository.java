package harmony.communityservice.guild.channel.repository.query;

import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.guild.domain.GuildId;
import java.util.List;

public interface ChannelQueryRepository {

    List<Channel> findListByGuildId(GuildId guildId);
}
