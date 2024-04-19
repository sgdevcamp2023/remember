package harmony.communityservice.common.event.mapper;

import harmony.communityservice.common.event.dto.ChannelCreatedEvent;
import harmony.communityservice.guild.channel.dto.RegisterChannelRequest;

public class ToChannelCreatedEventMapper {

    public static ChannelCreatedEvent convert(RegisterChannelRequest registerChannelRequest, Long channelId) {
        return ChannelCreatedEvent.builder()
                .type("CREATE-CHANNEL")
                .guildId(registerChannelRequest.guildId())
                .channelType(registerChannelRequest.type())
                .channelName(registerChannelRequest.name())
                .channelId(channelId)
                .categoryId(registerChannelRequest.categoryId())
                .build();
    }
}
