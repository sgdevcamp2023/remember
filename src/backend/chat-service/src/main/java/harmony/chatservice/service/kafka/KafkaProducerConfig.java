package harmony.chatservice.service.kafka;

import harmony.chatservice.dto.CommunityMessageDto;
import harmony.chatservice.dto.DirectMessageDto;
import harmony.chatservice.dto.EmojiDto;
import harmony.chatservice.dto.response.SessionDto;
import harmony.chatservice.dto.response.StateDto;
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

    // 커뮤니티 관련 producer
    public ProducerFactory<String, CommunityMessageDto> producerFactoryForCommunity() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    // 커뮤니티 관련 producer
    @Bean
    public KafkaTemplate<String, CommunityMessageDto> kafkaTemplateForCommunity() {
        return new KafkaTemplate<>(producerFactoryForCommunity());
    }

    // 다이렉트 관련 producer
    public ProducerFactory<String, DirectMessageDto> producerFactoryForDirect() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    // 다이렉트 관련 producer
    @Bean
    public KafkaTemplate<String, DirectMessageDto> kafkaTemplateForDirect() {
        return new KafkaTemplate<>(producerFactoryForDirect());
    }

    // 이모지 관련 producer
    public ProducerFactory<String, EmojiDto> producerFactoryForEmoji() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    // 이모지 관련 producer
    @Bean
    public KafkaTemplate<String, EmojiDto> kafkaTemplateForEmoji() {
        return new KafkaTemplate<>(producerFactoryForEmoji());
    }

    // 상태 관련 producer
    public ProducerFactory<String, StateDto> producerFactoryForState() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    // 상태 관련 producer
    @Bean
    public KafkaTemplate<String, StateDto> kafkaTemplateForState() {
        return new KafkaTemplate<>(producerFactoryForState());
    }

    // 세션 관련 producer
    public ProducerFactory<String, SessionDto> producerFactoryForSession() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    // 세션 관련 producer
    @Bean
    public KafkaTemplate<String, SessionDto> kafkaTemplateForSession() {
        return new KafkaTemplate<>(producerFactoryForSession());
    }
}