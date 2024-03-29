package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.community.command.dto.DeleteChannelRequest;
import harmony.communityservice.community.command.dto.RegisterChannelRequest;
import harmony.communityservice.community.command.repository.ChannelCommandRepository;
import harmony.communityservice.community.command.service.ChannelCommandService;
import harmony.communityservice.community.command.service.ChannelReadCommandService;
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
    private final GuildQueryService guildQueryService;
    private final ChannelReadCommandService channelReadCommandService;

    @Override
    public Long register(RegisterChannelRequest registerChannelRequest) {
        userReadQueryService.existsByUserIdAndGuildId(registerChannelRequest.userId(),
                registerChannelRequest.guildId());
        Channel channel = createChannel(registerChannelRequest);
        channelCommandRepository.save(channel);
        channelReadCommandService.register(registerChannelRequest.guildId(), channel);
        return channel.getChannelId();
    }

    private Channel createChannel(RegisterChannelRequest registerChannelRequest) {
        Guild guild = guildQueryService.searchByGuildId(registerChannelRequest.guildId());
        return ToChannelMapper.convert(registerChannelRequest, guild);
    }

    @Override
    public void delete(DeleteChannelRequest deleteChannelRequest) {
        userReadQueryService.existsByUserIdAndGuildId(deleteChannelRequest.userId(), deleteChannelRequest.guildId());
        channelReadCommandService.delete(deleteChannelRequest.channelId());
        channelCommandRepository.deleteByChannelId(deleteChannelRequest.channelId());
    }
}
