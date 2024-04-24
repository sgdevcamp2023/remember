package harmony.communityservice.guild.channel.repository.query.jpa;

import harmony.communityservice.guild.channel.domain.Channel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChannelQueryRepository extends JpaRepository<Channel, Long> {

    List<Channel> findChannelsByGuildId(Long guildId);
}
