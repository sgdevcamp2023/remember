package harmony.communityservice.common.config;

import harmony.communityservice.common.event.dto.produce.ExternalEvent;
import harmony.communityservice.common.service.EventProducer;
import harmony.communityservice.common.service.impl.KafkaEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final KafkaTemplate<String, ExternalEvent> ExternalEventKafkaTemplate;

    @Bean
    public EventProducer producerService() {
        return new KafkaEventProducer(ExternalEventKafkaTemplate);
    }
}
