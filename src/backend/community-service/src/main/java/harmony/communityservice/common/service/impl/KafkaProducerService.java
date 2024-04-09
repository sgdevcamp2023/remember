package harmony.communityservice.common.service.impl;

import harmony.communityservice.common.dto.CommunityEvent;
import harmony.communityservice.common.service.ProducerService;
import harmony.communityservice.guild.channel.dto.RegisterChannelRequest;
import harmony.communityservice.guild.domain.GuildRead;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class KafkaProducerService implements ProducerService {

    private final KafkaTemplate<String, CommunityEvent> kafkaTemplateForCommunity;
    @Value("${spring.kafka.producer.community-event-topic}")
    private String communityEvent;

    @Override
    public void publishGuildDeletionEvent(Long guildId) {
        CommunityEvent event = CommunityEvent.builder()
                .type("DELETE-GUILD")
                .guildId(guildId)
                .build();
        kafkaTemplateForCommunity.send(communityEvent, event);
    }

    @Override
    public void publishChannelCreationEvent(RegisterChannelRequest registerChannelRequest, Integer channelId) {
        CommunityEvent event = CommunityEvent.builder()
                .type("CREATE-CHANNEL")
                .guildId(registerChannelRequest.guildId())
                .channelType(registerChannelRequest.type())
                .channelName(registerChannelRequest.name())
                .channelId(channelId)
                .categoryId(registerChannelRequest.categoryId())
                .build();
        kafkaTemplateForCommunity.send(communityEvent, event);
    }

    @Override
    public void publishChannelDeletionEvent(Integer channelId) {
        CommunityEvent event = CommunityEvent.builder()
                .type("DELETE-CHANNEL")
                .channelId(channelId)
                .build();
        kafkaTemplateForCommunity.send(communityEvent, event);
    }

    @Override
    public void publishGuildCreationEvent(GuildRead guildRead) {
        CommunityEvent event = CommunityEvent.builder()
                .type("CREATE-GUILD")
                .guildId(guildRead.getGuildId())
                .guildReadId(guildRead.getGuildReadId())
                .name(guildRead.getGuildInfo().getName())
                .profile(guildRead.getGuildInfo().getProfile())
                .build();
        kafkaTemplateForCommunity.send(communityEvent, event);
    }
}
