package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.community.command.repository.ChannelReadCommandRepository;
import harmony.communityservice.community.command.service.ChannelReadCommandService;
import harmony.communityservice.community.domain.Channel;
import harmony.communityservice.community.domain.ChannelRead;
import harmony.communityservice.community.mapper.ToChannelReadMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChannelReadCommandServiceImpl implements ChannelReadCommandService {

    private final ChannelReadCommandRepository channelReadCommandRepository;

    @Override
    public void register(long guildId, Channel channel) {
        ChannelRead channelRead = ToChannelReadMapper.convert(channel, guildId);
        channelReadCommandRepository.save(channelRead);
    }

    @Override
    public void delete(long channelId) {
        channelReadCommandRepository.deleteByChannelId(channelId);
    }
}
