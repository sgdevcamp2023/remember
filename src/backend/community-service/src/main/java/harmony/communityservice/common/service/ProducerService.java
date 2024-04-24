package harmony.communityservice.common.service;

import harmony.communityservice.common.event.dto.produce.ChannelCreatedEvent;
import harmony.communityservice.common.event.dto.produce.ChannelDeletedEvent;
import harmony.communityservice.common.event.dto.produce.GuildCreatedEvent;
import harmony.communityservice.common.event.dto.produce.GuildDeletedEvent;

public interface ProducerService {
    void publishGuildDeletionEvent(GuildDeletedEvent event);

    void publishChannelCreationEvent(ChannelCreatedEvent event);

    void publishChannelDeletionEvent(ChannelDeletedEvent event);

    void publishGuildCreationEvent(GuildCreatedEvent event);
}
