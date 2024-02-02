package harmony.chatservice.service.kafka;

import harmony.chatservice.dto.response.ConnectionEventDto;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
public class StateConsumerConfig {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id.state}")
    private String groupName;

    @Bean
    public Map<String, Object> consumerConfigurationsForConnectionEvent() {
        Map<String,Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupName);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return config;
    }

    @Bean
    public ConsumerFactory<String, ConnectionEventDto> consumerFactoryForConnectionEvent() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigurationsForConnectionEvent(), new StringDeserializer(),
                new JsonDeserializer<>(ConnectionEventDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ConnectionEventDto> connectionEventListener() {
        ConcurrentKafkaListenerContainerFactory<String, ConnectionEventDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryForConnectionEvent());
        return factory;
    }
}
