package harmony.communityservice.common.service;

import harmony.communityservice.common.event.dto.produce.ExternalEvent;

public interface EventProducer {
    void publishEvent(ExternalEvent event);

}
