package harmony.communityservice.guild.channel.adapter.out.persistence;

import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChannelQueryRepository extends JpaRepository<ChannelEntity, ChannelIdJpaVO> {

    List<ChannelEntity> findChannelsByGuildId(GuildIdJpaVO guildId);
}
