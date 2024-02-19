package harmony.communityservice.common.service.impl;

import harmony.communityservice.common.dto.CommunityEventDto;
import harmony.communityservice.common.service.ProducerService;
import harmony.communityservice.community.domain.GuildRead;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private final KafkaTemplate<String, CommunityEventDto> kafkaTemplateForCommunity;
    @Value("${spring.kafka.producer.community-event-topic}")
    private String communityEvent;

    @Override
    public void sendDeleteGuild(Long guildId) {
        CommunityEventDto kafkaEventDto = CommunityEventDto.builder()
                .type("DELETE-GUILD")
                .guildId(guildId)
                .build();
        kafkaTemplateForCommunity.send(communityEvent, kafkaEventDto);
    }

    @Override
    public void sendCreateChannel(Long guildId, Long categoryId, Long channelId, String channelName,
                                  String channelType) {
        CommunityEventDto kafkaEventDto = CommunityEventDto.builder()
                .type("CREATE-CHANNEL")
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
        CommunityEventDto kafkaEventDto = CommunityEventDto.builder()
                .type("DELETE-CHANNEL")
                .channelReadId(channelId)
                .build();
        kafkaTemplateForCommunity.send(communityEvent, kafkaEventDto);
    }

    @Override
    public void sendCreateGuild(GuildRead guildRead) {
        CommunityEventDto kafkaEventDto = CommunityEventDto.builder()
                .type("CREATE-GUILD")
                .guildId(guildRead.getGuildId())
                .guildReadId(guildRead.getGuildReadId())
                .name(guildRead.getName())
                .profile(guildRead.getProfile())
                .build();
        kafkaTemplateForCommunity.send(communityEvent, kafkaEventDto);
    }
}
