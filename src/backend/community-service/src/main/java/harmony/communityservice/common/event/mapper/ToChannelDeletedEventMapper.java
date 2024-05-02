package harmony.communityservice.common.event.mapper;

import harmony.communityservice.common.event.dto.produce.ChannelDeletedEvent;

public class ToChannelDeletedEventMapper {

    public static ChannelDeletedEvent convert(Long channelId, Long guildId) {
        return ChannelDeletedEvent.builder()
                .type("DELETE-CHANNEL")
                .channelId(channelId)
                .guildId(guildId)
                .build();
    }
}

