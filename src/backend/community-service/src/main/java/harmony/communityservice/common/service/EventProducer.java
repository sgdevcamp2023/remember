package harmony.communityservice.common.service;

import harmony.communityservice.common.event.dto.produce.ExternalEvent;

public interface EventProducer {
    void publishGuildDeletionEvent(ExternalEvent event);

    void publishChannelCreationEvent(ExternalEvent event);

    void publishChannelDeletionEvent(ExternalEvent event);

    void publishGuildCreationEvent(ExternalEvent event);
}
