package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.common.dto.VerifyGuildMemberRequest;
import harmony.communityservice.community.command.dto.DeleteChannelRequest;
import harmony.communityservice.community.command.dto.RegisterChannelRequest;
import harmony.communityservice.community.command.repository.ChannelCommandRepository;
import harmony.communityservice.community.command.service.ChannelCommandService;
import harmony.communityservice.community.domain.Channel;
import harmony.communityservice.community.domain.Guild;
import harmony.communityservice.community.mapper.ToChannelMapper;
import harmony.communityservice.community.query.service.GuildQueryService;
import harmony.communityservice.community.query.service.UserReadQueryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChannelCommandServiceImpl implements ChannelCommandService {

    private final ChannelCommandRepository channelCommandRepository;
    private final UserReadQueryService userReadQueryService;

    @Override
    public Long register(RegisterChannelRequest registerChannelRequest) {
        userReadQueryService.existsByUserIdAndGuildId(
                new VerifyGuildMemberRequest(registerChannelRequest.userId(), registerChannelRequest.guildId()));
        Channel channel = createChannel(registerChannelRequest);
        channelCommandRepository.save(channel);
        return channel.getChannelId();
    }

    private Channel createChannel(RegisterChannelRequest registerChannelRequest) {
        return ToChannelMapper.convert(registerChannelRequest);
    }

    @Override
    public void delete(DeleteChannelRequest deleteChannelRequest) {
        userReadQueryService.existsByUserIdAndGuildId(
                new VerifyGuildMemberRequest(deleteChannelRequest.userId(), deleteChannelRequest.guildId()));
        channelCommandRepository.deleteByChannelId(deleteChannelRequest.channelId());
    }
}
