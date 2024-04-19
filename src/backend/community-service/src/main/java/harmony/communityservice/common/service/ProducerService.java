package harmony.communityservice.common.service;

import harmony.communityservice.common.event.dto.ChannelCreatedEvent;
import harmony.communityservice.common.event.dto.ChannelDeletedEvent;
import harmony.communityservice.common.event.dto.GuildCreatedEvent;
import harmony.communityservice.common.event.dto.GuildDeletedEvent;
import harmony.communityservice.guild.channel.dto.RegisterChannelRequest;

public interface ProducerService {
    void publishGuildDeletionEvent(GuildDeletedEvent event);

    void publishChannelCreationEvent(ChannelCreatedEvent event);

    void publishChannelDeletionEvent(ChannelDeletedEvent event);

    void publishGuildCreationEvent(GuildCreatedEvent event);
}
