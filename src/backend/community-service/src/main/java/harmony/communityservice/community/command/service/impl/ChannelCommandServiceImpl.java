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
        userReadQueryService.existsByUserIdAndGuildId(registerChannelRequest.getUserId(), registerChannelRequest.getGuildId());
        Guild guild = guildQueryService.searchByGuildId(registerChannelRequest.getGuildId());
        Channel channel = ToChannelMapper.convert(registerChannelRequest, guild);
        channelCommandRepository.save(channel);
        channelReadCommandService.register(registerChannelRequest.getGuildId(), channel);
        return channel.getChannelId();
    }

    @Override
    public void delete(DeleteChannelRequest deleteChannelRequest) {
        userReadQueryService.existsByUserIdAndGuildId(deleteChannelRequest.getUserId(), deleteChannelRequest.getGuildId());
        channelReadCommandService.delete(deleteChannelRequest.getChannelId());
        channelCommandRepository.deleteByChannelId(deleteChannelRequest.getChannelId());
    }
}
