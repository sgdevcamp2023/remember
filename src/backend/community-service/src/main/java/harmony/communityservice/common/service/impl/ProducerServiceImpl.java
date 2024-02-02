package harmony.communityservice.common.service.impl;

import harmony.communityservice.common.dto.KafkaEventDto;
import harmony.communityservice.common.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private final KafkaTemplate<String, KafkaEventDto> kafkaTemplateForCommunity;
    @Value("${spring.kafka.producer.community-event}")
    private String communityEvent;

    @Override
    public void sendDeleteGuild(Long guildId) {
        KafkaEventDto kafkaEventDto = KafkaEventDto.builder()
                .eventType("DELETE-GUILD")
                .guildId(guildId)
                .build();
        kafkaTemplateForCommunity.send(communityEvent, kafkaEventDto);
    }

    @Override
    public void sendCreateChannel(Long guildId, Long categoryId, Long channelId, String channelName,
                                  String channelType) {
        KafkaEventDto kafkaEventDto = KafkaEventDto.builder()
                .eventType("CREATE-CHANNEL")
                .guildId(guildId)
                .channelType(channelType)
                .channelName(channelName)
                .channelReadId(channelId)
                .categoryId(categoryId)
                .build();
        kafkaTemplateForCommunity.send(communityEvent, kafkaEventDto);
    }

    @Override
    public void sendDeleteChannel(Long channelId) {
        KafkaEventDto kafkaEventDto = KafkaEventDto.builder()
                .eventType("DELETE-CHANNEL")
                .channelReadId(channelId)
                .build();
        kafkaTemplateForCommunity.send(communityEvent, kafkaEventDto);
    }
}
