package harmony.communityservice.common.config;

import harmony.communityservice.common.event.dto.ChannelCreatedEvent;
import harmony.communityservice.common.event.dto.ChannelDeletedEvent;
import harmony.communityservice.common.event.dto.GuildCreatedEvent;
import harmony.communityservice.common.event.dto.GuildDeletedEvent;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@EnableKafka
@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> producerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return config;
    }

    public ProducerFactory<String, ChannelCreatedEvent> channelCreatedEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, ChannelCreatedEvent> channelCreatedEventKafkaTemplate() {
        return new KafkaTemplate<>(channelCreatedEventProducerFactory());
    }

    public ProducerFactory<String, ChannelDeletedEvent> channelDeletedEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, ChannelDeletedEvent> channelDeletedEventKafkaTemplate() {
        return new KafkaTemplate<>(channelDeletedEventProducerFactory());
    }

    public ProducerFactory<String, GuildCreatedEvent> guildCreatedEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, GuildCreatedEvent> guildCreatedEventKafkaTemplate() {
        return new KafkaTemplate<>(guildCreatedEventProducerFactory());
    }

    public ProducerFactory<String, GuildDeletedEvent> guildDeletedEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, GuildDeletedEvent> guildDeletedEventKafkaTemplate() {
        return new KafkaTemplate<>(guildDeletedEventProducerFactory());
    }

}
