package harmony.communityservice.common.service.impl;

import harmony.communityservice.common.event.dto.produce.ExternalEvent;
import harmony.communityservice.common.service.EventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class KafkaEventProducer implements EventProducer {

    private final KafkaTemplate<String, ExternalEvent> externalEventKafkaTemplate;
    @Value("${spring.kafka.producer.community-event-topic}")
    private String communityEvent;

    @Override
    public void publishEvent(ExternalEvent event) {
        externalEventKafkaTemplate.send(communityEvent, event);
    }
}
