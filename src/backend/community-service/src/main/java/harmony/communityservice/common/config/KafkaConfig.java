package harmony.communityservice.common.config;

import harmony.communityservice.common.event.dto.ChannelCreatedEvent;
import harmony.communityservice.common.event.dto.ChannelDeletedEvent;
import harmony.communityservice.common.event.dto.GuildCreatedEvent;
import harmony.communityservice.common.event.dto.GuildDeletedEvent;
import harmony.communityservice.common.service.ProducerService;
import harmony.communityservice.common.service.impl.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final KafkaTemplate<String, GuildCreatedEvent> guildCreatedEventKafkaTemplate;
    private final KafkaTemplate<String, GuildDeletedEvent> guildDeletedEventKafkaTemplate;
    private final KafkaTemplate<String, ChannelCreatedEvent> channelCreatedEventKafkaTemplate;
    private final KafkaTemplate<String, ChannelDeletedEvent> channelDeletedEventKafkaTemplate;

    @Bean
    public ProducerService producerService() {
        return new KafkaProducerService(guildCreatedEventKafkaTemplate, guildDeletedEventKafkaTemplate,
                channelCreatedEventKafkaTemplate, channelDeletedEventKafkaTemplate);
    }
}
