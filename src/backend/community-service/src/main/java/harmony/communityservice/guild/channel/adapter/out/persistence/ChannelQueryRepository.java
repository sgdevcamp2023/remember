package harmony.communityservice.guild.channel.adapter.out.persistence;

import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface ChannelQueryRepository extends JpaRepository<ChannelEntity, ChannelIdJpaVO> {

    @Query("select c from ChannelEntity c where c.guildId = :guildId")
    List<ChannelEntity> findChannelsByGuildId(@Param("guildId") GuildIdJpaVO guildId);
}
