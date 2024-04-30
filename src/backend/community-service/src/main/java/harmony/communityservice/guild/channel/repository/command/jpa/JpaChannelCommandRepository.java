package harmony.communityservice.guild.channel.repository.command.jpa;

import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.channel.domain.ChannelId;
import harmony.communityservice.guild.channel.domain.ChannelType;
import harmony.communityservice.guild.guild.domain.GuildId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaChannelCommandRepository extends JpaRepository<Channel, ChannelId> {

    @Modifying
    @Query("delete from Channel c where c.guildId = :guildId")
    void deleteChannelsByGuildId(@Param("guildId") GuildId guildId);

    @Query("select c.channelId from Channel c where c.guildId = :guildId and c.channelType = :type")
    List<ChannelId> findChannelIdsByGuildIdAndType(@Param("guildId") GuildId guildId, @Param("type") ChannelType type);
}
