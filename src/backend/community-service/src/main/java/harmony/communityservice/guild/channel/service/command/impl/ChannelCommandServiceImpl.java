package harmony.communityservice.guild.channel.service.command.impl;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.guild.channel.dto.DeleteChannelRequest;
import harmony.communityservice.guild.channel.dto.RegisterChannelRequest;
import harmony.communityservice.guild.channel.mapper.ToChannelMapper;
import harmony.communityservice.guild.channel.repository.command.ChannelCommandRepository;
import harmony.communityservice.guild.channel.service.command.ChannelCommandService;
import harmony.communityservice.guild.domain.Channel;
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
        channelCommandRepository.deleteByChannelId(deleteChannelRequest.channelId());
    }
}
