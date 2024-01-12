package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Channel;
import harmony.communityservice.community.domain.ChannelRead;

public class ToChannelReadMapper {

    public static ChannelRead convert(Channel channel, long guildId) {
        return ChannelRead.builder()
                .channelReadId(channel.getChannelId())
                .type(channel.getType())
                .name(channel.getName())
                .categoryId(channel.getCategoryId())
                .guildId(guildId)
                .build();
    }
}
