package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.community.command.dto.ChannelDeleteRequestDto;
import harmony.communityservice.community.command.dto.ChannelRegistrationRequestDto;
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
    public Long registration(ChannelRegistrationRequestDto requestDto) {
        userReadQueryService.existsUserIdAndGuildId(requestDto.getUserId(), requestDto.getGuildId());
        Guild guild = guildQueryService.findByGuildId(requestDto.getGuildId());
        Channel channel = ToChannelMapper.convert(requestDto, guild);
        channelCommandRepository.save(channel);
        channelReadCommandService.registration(requestDto.getGuildId(), channel);
        return channel.getChannelId();
    }

    @Override
    public void remove(ChannelDeleteRequestDto requestDto) {
        userReadQueryService.existsUserIdAndGuildId(requestDto.getUserId(), requestDto.getGuildId());
        channelReadCommandService.remove(requestDto.getChannelId());
        channelCommandRepository.deleteByChannelId(requestDto.getChannelId());
    }
}
