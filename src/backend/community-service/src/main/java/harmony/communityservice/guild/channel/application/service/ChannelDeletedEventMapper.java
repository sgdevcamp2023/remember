package harmony.communityservice.guild.channel.application.service;

import harmony.communityservice.common.event.dto.produce.ChannelDeletedEvent;

public class ChannelDeletedEventMapper {

    public static ChannelDeletedEvent convert(Long channelId, Long guildId) {
        return ChannelDeletedEvent.builder()
                .type("DELETE-CHANNEL")
                .channelId(channelId)
                .guildId(guildId)
                .build();
    }
}

