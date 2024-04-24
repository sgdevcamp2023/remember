package harmony.communityservice.guild.channel.service.command.impl;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.common.event.Events;
import harmony.communityservice.common.event.dto.ChannelCreatedEvent;
import harmony.communityservice.common.event.dto.ChannelDeletedEvent;
import harmony.communityservice.common.event.mapper.ToChannelCreatedEventMapper;
import harmony.communityservice.common.event.mapper.ToChannelDeletedEventMapper;
import harmony.communityservice.guild.channel.dto.DeleteChannelRequest;
import harmony.communityservice.guild.channel.dto.RegisterChannelRequest;
import harmony.communityservice.guild.channel.mapper.ToChannelMapper;
import harmony.communityservice.guild.channel.repository.command.ChannelCommandRepository;
import harmony.communityservice.guild.channel.service.command.ChannelCommandService;
import harmony.communityservice.guild.channel.domain.Channel;
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
        ChannelCreatedEvent event = ToChannelCreatedEventMapper.convert(registerChannelRequest, channel.getChannelId());
        Events.send(event);
        return channel.getChannelId();
    }

    @Override
    @AuthorizeGuildMember
    public void delete(DeleteChannelRequest deleteChannelRequest) {
        channelCommandRepository.deleteById(deleteChannelRequest.channelId());
        ChannelDeletedEvent event = ToChannelDeletedEventMapper.convert(deleteChannelRequest);
        Events.send(event);
    }

    @Override
    public void deleteByGuildId(Long guildId) {
        channelCommandRepository.deleteByGuildId(guildId);
    }
}
