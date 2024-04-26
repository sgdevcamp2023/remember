package harmony.communityservice.guild.channel.repository.query.jpa;

import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.channel.domain.ChannelId;
import harmony.communityservice.guild.guild.domain.GuildId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChannelQueryRepository extends JpaRepository<Channel, ChannelId> {

    List<Channel> findChannelsByGuildId(GuildId guildId);
}
