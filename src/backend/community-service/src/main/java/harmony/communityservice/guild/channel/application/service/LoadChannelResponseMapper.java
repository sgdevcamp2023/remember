package harmony.communityservice.guild.channel.application.service;

import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.channel.application.port.in.LoadChannelResponse;

public class LoadChannelResponseMapper {

    public static LoadChannelResponse convert(Channel channel) {
        return LoadChannelResponse.builder()
                .channelId(channel.getChannelId().getId())
                .channelName(channel.getName())
                .channelType(channel.getType())
                .categoryId(channel.getCategoryId().getId())
                .guildId(channel.getGuildId().getId())
                .build();
    }
}
