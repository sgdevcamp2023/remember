package harmony.communityservice.guild.channel.adapter.out.persistence;

import harmony.communityservice.common.annotation.PersistenceAdapter;
import harmony.communityservice.guild.channel.application.port.out.LoadChannelsPort;
import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import java.util.List;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class ChannelQueryPersistenceAdapter implements LoadChannelsPort {

    private final ChannelQueryRepository channelQueryRepository;

    @Override
    public List<Channel> loadChannels(GuildId guildId) {
        List<ChannelEntity> channelEntities = channelQueryRepository.findChannelsByGuildId(
                GuildIdJpaVO.make(guildId.getId()));
        return channelEntities.stream()
                .map(ChannelMapper::convert)
                .toList();
    }
}
