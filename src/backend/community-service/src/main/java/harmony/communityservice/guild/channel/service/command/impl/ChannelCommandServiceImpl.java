package harmony.communityservice.guild.channel.service.command.impl;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.common.event.Events;
import harmony.communityservice.common.event.dto.inner.DeleteBoardsEvent;
import harmony.communityservice.common.event.dto.inner.DeleteBoardsInGuildEvent;
import harmony.communityservice.common.event.dto.produce.ChannelDeletedEvent;
import harmony.communityservice.common.event.mapper.ToChannelDeletedEventMapper;
import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.channel.domain.ChannelType;
import harmony.communityservice.guild.channel.dto.DeleteChannelRequest;
import harmony.communityservice.guild.channel.dto.RegisterChannelRequest;
import harmony.communityservice.guild.channel.mapper.ToChannelMapper;
import harmony.communityservice.guild.channel.repository.command.ChannelCommandRepository;
import harmony.communityservice.guild.channel.service.command.ChannelCommandService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class ChannelCommandServiceImpl implements ChannelCommandService {

    private final ChannelCommandRepository channelCommandRepository;

    @Override
    @AuthorizeGuildMember
    public Long register(RegisterChannelRequest registerChannelRequest) {
        Channel channel = ToChannelMapper.convert(registerChannelRequest);
        channelCommandRepository.save(channel);
        return channel.getChannelId();
    }

    @Override
    @AuthorizeGuildMember
    public void delete(DeleteChannelRequest deleteChannelRequest) {
        channelCommandRepository.deleteById(deleteChannelRequest.channelId());
        ChannelDeletedEvent event = ToChannelDeletedEventMapper.convert(deleteChannelRequest);
        Events.send(event);
        if (deleteChannelRequest.type().equals("FORUM")) {
            Events.send(new DeleteBoardsEvent(deleteChannelRequest.channelId(), deleteChannelRequest.userId()));
        }
    }

    @Override
    public void deleteByGuildId(Long guildId) {
        List<Long> channelIds = channelCommandRepository.findIdsByGuildIdAndType(guildId, ChannelType.FORUM);
        Events.send(new DeleteBoardsInGuildEvent(channelIds));
        channelCommandRepository.deleteByGuildId(guildId);
    }
}
