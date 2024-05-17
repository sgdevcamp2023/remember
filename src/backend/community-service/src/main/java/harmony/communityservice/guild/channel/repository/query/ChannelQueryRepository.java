package harmony.communityservice.guild.channel.repository.query;

import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO;
import java.util.List;

public interface ChannelQueryRepository {

    List<Channel> findListByGuildId(GuildIdJpaVO guildId);
}
