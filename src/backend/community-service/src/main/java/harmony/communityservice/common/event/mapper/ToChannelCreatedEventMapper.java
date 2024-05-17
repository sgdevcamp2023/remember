package harmony.communityservice.common.event.mapper;

import harmony.communityservice.common.event.dto.produce.ChannelCreatedEvent;
import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelEntity;

public class ToChannelCreatedEventMapper {

    public static ChannelCreatedEvent convert(ChannelEntity channel) {
        return ChannelCreatedEvent.builder()
                .type("CREATE-CHANNEL")
                .guildId(channel.getGuildId().getId())
                .channelType(channel.getChannelType())
                .channelName(channel.getName())
                .channelId(channel.getChannelId().getId())
                .categoryId(channel.getCategoryId().getId())
                .build();
    }
}
