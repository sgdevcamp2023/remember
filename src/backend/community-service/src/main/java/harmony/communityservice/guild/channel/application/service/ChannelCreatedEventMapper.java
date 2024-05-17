package harmony.communityservice.guild.channel.application.service;

import harmony.communityservice.common.event.dto.produce.ChannelCreatedEvent;
import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;

public class ChannelCreatedEventMapper {

    public static ChannelCreatedEvent convert(Channel channel, ChannelId channelId) {
        return ChannelCreatedEvent.builder()
                .type("CREATE-CHANNEL")
                .guildId(channel.getGuildId().getId())
                .channelType(channel.getType())
                .channelName(channel.getName())
                .channelId(channelId.getId())
                .categoryId(channel.getCategoryId().getId())
                .build();
    }
}
