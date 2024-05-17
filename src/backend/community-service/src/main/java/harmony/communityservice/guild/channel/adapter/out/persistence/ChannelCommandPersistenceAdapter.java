package harmony.communityservice.guild.channel.adapter.out.persistence;

import harmony.communityservice.common.annotation.PersistenceAdapter;
import harmony.communityservice.guild.channel.application.port.out.DeleteChannelPort;
import harmony.communityservice.guild.channel.application.port.out.DeleteGuildChannelsPort;
import harmony.communityservice.guild.channel.application.port.out.LoadForumChannelIdsPort;
import harmony.communityservice.guild.channel.application.port.out.RegisterChannelPort;
import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import harmony.communityservice.guild.channel.domain.ChannelType;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import java.util.List;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class ChannelCommandPersistenceAdapter implements RegisterChannelPort, DeleteChannelPort,
        LoadForumChannelIdsPort, DeleteGuildChannelsPort {

    private final ChannelCommandRepository channelCommandRepository;

    @Override
    public ChannelId register(Channel channel) {
        ChannelEntity channelEntity = ChannelEntityMapper.convert(channel);
        channelCommandRepository.save(channelEntity);
        return ChannelId.make(channelEntity.getChannelId().getId());
    }

    @Override
    public void deleteById(ChannelId channelId) {
        channelCommandRepository.deleteById(ChannelIdJpaVO.make(channelId.getId()));
    }

    @Override
    public List<ChannelId> loadForumChannelIds(GuildId guildId, ChannelType channelType) {
        List<ChannelIdJpaVO> channelIdJpaVOS = channelCommandRepository.findChannelIdsByGuildIdAndType(
                GuildIdJpaVO.make(guildId.getId()),
                ChannelTypeJpaEnum.valueOf(channelType.name()));
        return channelIdJpaVOS.stream()
                .map(channelIdJpaVO -> ChannelId.make(channelIdJpaVO.getId()))
                .toList();
    }

    @Override
    public void deleteByGuildId(GuildId guildId) {
        channelCommandRepository.deleteChannelsByGuildId(GuildIdJpaVO.make(guildId.getId()));
    }
}
