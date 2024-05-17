package harmony.communityservice.guild.channel.adapter.out.persistence;

import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface ChannelCommandRepository extends JpaRepository<ChannelEntity, ChannelIdJpaVO> {

    @Modifying
    @Query("delete from ChannelEntity c where c.guildId = :guildId")
    void deleteChannelsByGuildId(@Param("guildId") GuildIdJpaVO guildId);

    @Query("select c.channelId from ChannelEntity c where c.guildId = :guildId and c.channelType = :type")
    List<ChannelIdJpaVO> findChannelIdsByGuildIdAndType(@Param("guildId") GuildIdJpaVO guildId, @Param("type") ChannelTypeJpaEnum type);
}
