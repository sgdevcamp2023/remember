package harmony.communityservice.common.service.impl;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import harmony.communityservice.common.event.dto.produce.ExternalEvent;
import harmony.communityservice.common.outbox.ExternalEventRecord;
import harmony.communityservice.common.outbox.ExternalEventType;
import harmony.communityservice.common.outbox.SentType;
import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelTypeJpaEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
class KafkaEventProducerTest {

    @Mock
    private KafkaTemplate<String, ExternalEvent> externalEventKafkaTemplate;

    @Value("${spring.kafka.producer.community-event-topic}")
    private String communityEvent;

    private KafkaEventProducer kafkaEventProducer;

    @BeforeEach
    void setting() {
        kafkaEventProducer = new KafkaEventProducer(externalEventKafkaTemplate);
    }

    @Test
    @DisplayName("길드 생성 이벤트 kafka 전송 테스트")
    void send_guild_create_event() {
        ExternalEventRecord externalEventRecord = ExternalEventRecord.builder()
                .type(ExternalEventType.CREATED_GUILD)
                .guildId(1L)
                .name("guild_name")
                .profile("https://cdn.com/guild")
                .sentType(SentType.INIT)
                .build();
        given(externalEventKafkaTemplate.send(communityEvent, externalEventRecord.make())).willReturn(null);

        kafkaEventProducer.publishGuildCreationEvent(externalEventRecord.make());

        then(externalEventKafkaTemplate).should(times(1)).send(communityEvent, externalEventRecord.make());
    }

    @Test
    @DisplayName("길드 삭제 이벤트 kafka 전송 테스트")
    void send_guild_deleted_event() {
        ExternalEventRecord externalEventRecord = ExternalEventRecord.builder()
                .type(ExternalEventType.DELETED_GUILD)
                .guildId(1L)
                .sentType(SentType.INIT)
                .build();
        given(externalEventKafkaTemplate.send(communityEvent, externalEventRecord.make())).willReturn(null);

        kafkaEventProducer.publishGuildDeletionEvent(externalEventRecord.make());

        then(externalEventKafkaTemplate).should(times(1)).send(communityEvent, externalEventRecord.make());
    }

    @Test
    @DisplayName("채널 생성 이벤트 kafka 전송 테스트")
    void send_channel_created_event() {
        ExternalEventRecord externalEventRecord = ExternalEventRecord.builder()
                .type(ExternalEventType.CREATED_CHANNEL)
                .sentType(SentType.INIT)
                .guildId(1L)
                .channelId(1L)
                .categoryId(1L)
                .channelName("channel_name")
                .channelType(ChannelTypeJpaEnum.valueOf("FORUM"))
                .build();
        given(externalEventKafkaTemplate.send(communityEvent, externalEventRecord.make())).willReturn(null);

        kafkaEventProducer.publishChannelCreationEvent(externalEventRecord.make());

        then(externalEventKafkaTemplate).should(times(1)).send(communityEvent, externalEventRecord.make());
    }

    @Test
    @DisplayName("채널 삭제 이벤트 kafka 전송 테스트")
    void send_channel_deleted_event() {
        ExternalEventRecord externalEventRecord = ExternalEventRecord.builder()
                .type(ExternalEventType.DELETED_CHANNEL)
                .guildId(1L)
                .channelId(1L)
                .sentType(SentType.INIT)
                .build();
        given(externalEventKafkaTemplate.send(communityEvent, externalEventRecord.make())).willReturn(null);

        kafkaEventProducer.publishChannelDeletionEvent(externalEventRecord.make());

        then(externalEventKafkaTemplate).should(times(1)).send(communityEvent, externalEventRecord.make());
    }
}