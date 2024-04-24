package harmony.communityservice.common.event.mapper;

import harmony.communityservice.common.event.dto.produce.ChannelDeletedEvent;
import harmony.communityservice.guild.channel.dto.DeleteChannelRequest;

public class ToChannelDeletedEventMapper {

    public static ChannelDeletedEvent convert(DeleteChannelRequest deleteChannelRequest) {
        return ChannelDeletedEvent.builder()
                .type("DELETE-CHANNEL")
                .channelId(deleteChannelRequest.channelId())
                .guildId(deleteChannelRequest.guildId())
                .build();
    }
}

