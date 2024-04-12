package harmony.communityservice.guild.channel.repository.command.jpa;

import harmony.communityservice.guild.domain.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChannelCommandRepository extends JpaRepository<Channel, Long> {

    void deleteChannelsByGuildId(Long guildId);
}
