package harmony.communityservice.common.service.impl;

import harmony.communityservice.common.event.dto.produce.ChannelCreatedEvent;
import harmony.communityservice.common.event.dto.produce.ChannelDeletedEvent;
import harmony.communityservice.common.event.dto.produce.GuildCreatedEvent;
import harmony.communityservice.common.event.dto.produce.GuildDeletedEvent;
import harmony.communityservice.common.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class KafkaProducerService implements ProducerService {

    private final KafkaTemplate<String, GuildCreatedEvent> guildCreatedEventKafkaTemplate;
    private final KafkaTemplate<String, GuildDeletedEvent> guildDeletedEventKafkaTemplate;
    private final KafkaTemplate<String, ChannelCreatedEvent> channelCreatedEventKafkaTemplate;
    private final KafkaTemplate<String, ChannelDeletedEvent> channelDeletedEventKafkaTemplate;
    @Value("${spring.kafka.producer.community-event-topic}")
    private String communityEvent;

    @Override
    public void publishGuildDeletionEvent(GuildDeletedEvent event) {
        guildDeletedEventKafkaTemplate.send(communityEvent, event);
    }

    @Override
    public void publishChannelCreationEvent(ChannelCreatedEvent event) {
        channelCreatedEventKafkaTemplate.send(communityEvent, event);
    }

    @Override
    public void publishChannelDeletionEvent(ChannelDeletedEvent event) {

        channelDeletedEventKafkaTemplate.send(communityEvent, event);
    }

    @Override
    public void publishGuildCreationEvent(GuildCreatedEvent event) {
        guildCreatedEventKafkaTemplate.send(communityEvent, event);
    }
}
