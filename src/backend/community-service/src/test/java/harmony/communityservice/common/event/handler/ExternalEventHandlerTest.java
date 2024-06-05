package harmony.communityservice.common.event.handler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;

import harmony.communityservice.common.event.dto.produce.ChannelCreatedEvent;
import harmony.communityservice.common.event.dto.produce.ChannelDeletedEvent;
import harmony.communityservice.common.event.dto.produce.GuildCreatedEvent;
import harmony.communityservice.common.event.dto.produce.GuildDeletedEvent;
import harmony.communityservice.common.outbox.ExternalEventOutBoxMapper;
import harmony.communityservice.common.outbox.ExternalEventRecord;
import harmony.communityservice.common.outbox.ExternalEventType;
import harmony.communityservice.common.outbox.SentType;
import harmony.communityservice.common.service.EventProducer;
import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelTypeJpaEnum;
import harmony.communityservice.guild.channel.domain.ChannelType;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExternalEventHandlerTest {

    @Mock
    private EventProducer eventProducer;

    @Mock
    private ExternalEventOutBoxMapper outBoxMapper;

    private ExternalEventHandler externalEventHandler;

    @BeforeEach
    void setting() {
        externalEventHandler = new ExternalEventHandler(eventProducer, outBoxMapper);
    }

    @Test
    @DisplayName("길드 등록 이벤트 브로커 발행 저장 테스트")
    void register_guild_event_external_before() {
        GuildCreatedEvent guildCreatedEvent = new GuildCreatedEvent(1L, "CREATED_GUILD", "guild",
                "httpps://cdn.com/test");
        willDoNothing().given(outBoxMapper).insertExternalEventRecord(argThat(record ->
                record.getSentType().equals(SentType.INIT) &&
                        record.getType().equals(ExternalEventType.CREATED_GUILD) &&
                        record.getGuildId().equals(guildCreatedEvent.getGuildId()) &&
                        record.getName().equals(guildCreatedEvent.getName()) &&
                        record.getProfile().equals(guildCreatedEvent.getProfile())));

        externalEventHandler.guildCreatedEventBeforeHandler(guildCreatedEvent);

        then(outBoxMapper).should(times(1)).insertExternalEventRecord(any(ExternalEventRecord.class));
    }

    @Test
    @DisplayName("길드 등록 이벤트 브로커 발행 테스트")
    void register_guild_event_external_after() {
        GuildCreatedEvent guildCreatedEvent = new GuildCreatedEvent(1L, "CREATED_GUILD", "guild",
                "https://cdn.com/test");
        ExternalEventRecord record = ExternalEventRecord.builder()
                .eventId(1L)
                .type(ExternalEventType.CREATED_GUILD)
                .guildId(guildCreatedEvent.getGuildId())
                .name(guildCreatedEvent.getName())
                .profile(guildCreatedEvent.getProfile())
                .sentType(SentType.INIT)
                .build();
        given(outBoxMapper.findExternalEventRecord(any(ExternalEventRecord.class))).willReturn(Optional.of(record));
        willDoNothing().given(eventProducer).publishEvent(record.make());
        willDoNothing().given(outBoxMapper).updateExternalEventRecord(SentType.SEND_SUCCESS, record.getEventId());

        externalEventHandler.guildCreatedEventAfterHandler(guildCreatedEvent);

        then(outBoxMapper).should(times(1)).findExternalEventRecord(any(ExternalEventRecord.class));
        then(eventProducer).should(times(1)).publishEvent(record.make());
        then(outBoxMapper).should(times(1)).updateExternalEventRecord(SentType.SEND_SUCCESS, record.getEventId());
    }

    @Test
    @DisplayName("길드 등록 이벤트 브로커 예외 테스트")
    void register_guild_event_external_exception() {
        GuildCreatedEvent guildCreatedEvent = new GuildCreatedEvent(1L, "CREATED_GUILD", "guild",
                "https://cdn.com/test");
        ExternalEventRecord record = ExternalEventRecord.builder()
                .eventId(1L)
                .type(ExternalEventType.CREATED_GUILD)
                .guildId(guildCreatedEvent.getGuildId())
                .name(guildCreatedEvent.getName())
                .profile(guildCreatedEvent.getProfile())
                .sentType(SentType.INIT)
                .build();
        given(outBoxMapper.findExternalEventRecord(any(ExternalEventRecord.class))).willReturn(Optional.of(record));
        willThrow(RuntimeException.class).given(eventProducer).publishEvent(record.make());
        willDoNothing().given(outBoxMapper).updateExternalEventRecord(SentType.SEND_FAIL, record.getEventId());

        externalEventHandler.guildCreatedEventAfterHandler(guildCreatedEvent);

        then(outBoxMapper).should(times(1)).findExternalEventRecord(any(ExternalEventRecord.class));
        then(eventProducer).should(times(1)).publishEvent(record.make());
        then(outBoxMapper).should(times(1)).updateExternalEventRecord(SentType.SEND_FAIL, record.getEventId());
    }

    @Test
    @DisplayName("길드 삭제 이벤트 브로커 저장 테스트")
    void delete_guild_event_external_before() {
        GuildDeletedEvent guildDeletedEvent = new GuildDeletedEvent(1L, "DELETED_GUILD");
        willDoNothing().given(outBoxMapper).insertExternalEventRecord(argThat(record ->
                record.getSentType().equals(SentType.INIT) &&
                        record.getType().equals(ExternalEventType.DELETED_GUILD) &&
                        record.getGuildId().equals(guildDeletedEvent.getGuildId())));

        externalEventHandler.guildDeletedEventBeforeHandler(guildDeletedEvent);

        then(outBoxMapper).should(times(1)).insertExternalEventRecord(any(ExternalEventRecord.class));
    }

    @Test
    @DisplayName("길드 삭제 이벤트 브로커 발행 테스트")
    void delete_guild_event_external_after() {
        GuildDeletedEvent guildDeletedEvent = new GuildDeletedEvent(1L, "DELETED_GUILD");
        ExternalEventRecord record = ExternalEventRecord.builder()
                .eventId(1L)
                .type(ExternalEventType.DELETED_GUILD)
                .guildId(guildDeletedEvent.getGuildId())
                .sentType(SentType.INIT)
                .build();
        given(outBoxMapper.findExternalEventRecord(any(ExternalEventRecord.class))).willReturn(Optional.of(record));
        willDoNothing().given(eventProducer).publishEvent(record.make());
        willDoNothing().given(outBoxMapper).updateExternalEventRecord(SentType.SEND_SUCCESS, record.getEventId());

        externalEventHandler.guildDeletedEventAfterHandler(guildDeletedEvent);

        then(outBoxMapper).should(times(1)).findExternalEventRecord(any(ExternalEventRecord.class));
        then(eventProducer).should(times(1)).publishEvent(record.make());
        then(outBoxMapper).should(times(1)).updateExternalEventRecord(SentType.SEND_SUCCESS, record.getEventId());
    }

    @Test
    @DisplayName("길드 삭제 이벤트 브로커 예외 테스트")
    void delete_guild_event_external_exception() {
        GuildDeletedEvent guildDeletedEvent = new GuildDeletedEvent(1L, "DELETED_GUILD");
        ExternalEventRecord record = ExternalEventRecord.builder()
                .eventId(1L)
                .type(ExternalEventType.DELETED_GUILD)
                .guildId(guildDeletedEvent.getGuildId())
                .sentType(SentType.INIT)
                .build();
        given(outBoxMapper.findExternalEventRecord(any(ExternalEventRecord.class))).willReturn(Optional.of(record));
        willThrow(RuntimeException.class).given(eventProducer).publishEvent(record.make());
        willDoNothing().given(outBoxMapper).updateExternalEventRecord(SentType.SEND_FAIL, record.getEventId());

        externalEventHandler.guildDeletedEventAfterHandler(guildDeletedEvent);

        then(outBoxMapper).should(times(1)).findExternalEventRecord(any(ExternalEventRecord.class));
        then(eventProducer).should(times(1)).publishEvent(record.make());
        then(outBoxMapper).should(times(1)).updateExternalEventRecord(SentType.SEND_FAIL, record.getEventId());
    }

    @Test
    @DisplayName("채널 생성 이벤트 브로커 저장 이벤트")
    void register_channel_event_external_before() {
        ChannelCreatedEvent channelCreatedEvent = new ChannelCreatedEvent(1L, "CREATED_CHANNEL", 1L, 1L, "channel",
                ChannelType.FORUM);
        willDoNothing().given(outBoxMapper).insertExternalEventRecord(argThat(record ->
                record.getType().equals(ExternalEventType.CREATED_CHANNEL) &&
                        record.getSentType().equals(SentType.INIT) &&
                        record.getGuildId().equals(channelCreatedEvent.getGuildId()) &&
                        record.getChannelId().equals(channelCreatedEvent.getChannelId()) &&
                        record.getCategoryId().equals(channelCreatedEvent.getCategoryId()) &&
                        record.getChannelName().equals(channelCreatedEvent.getChannelName()) &&
                        record.getChannelType()
                                .equals(ChannelTypeJpaEnum.valueOf(channelCreatedEvent.getChannelType().name())
                                )));

        externalEventHandler.channelCreatedEventBeforeHandler(channelCreatedEvent);

        then(outBoxMapper).should(times(1)).insertExternalEventRecord(any(ExternalEventRecord.class));
    }

    @Test
    @DisplayName("채널 생성 이벤트 브로커 발행 이벤트")
    void register_channel_event_external_after() {
        ChannelCreatedEvent channelCreatedEvent = new ChannelCreatedEvent(1L, "CREATED_CHANNEL", 1L, 1L, "channel",
                ChannelType.FORUM);
        ExternalEventRecord record = ExternalEventRecord.builder()
                .eventId(1L)
                .type(ExternalEventType.CREATED_CHANNEL)
                .sentType(SentType.INIT)
                .guildId(channelCreatedEvent.getGuildId())
                .channelId(channelCreatedEvent.getChannelId())
                .categoryId(channelCreatedEvent.getCategoryId())
                .channelName(channelCreatedEvent.getChannelName())
                .channelType(ChannelTypeJpaEnum.valueOf(channelCreatedEvent.getChannelType().name()))
                .build();
        given(outBoxMapper.findExternalEventRecord(any(ExternalEventRecord.class))).willReturn(Optional.of(record));
        willDoNothing().given(eventProducer).publishEvent(record.make());
        willDoNothing().given(outBoxMapper).updateExternalEventRecord(SentType.SEND_SUCCESS,record.getEventId());

        externalEventHandler.channelCreatedEventAfterHandler(channelCreatedEvent);

        then(outBoxMapper).should(times(1)).findExternalEventRecord(any(ExternalEventRecord.class));
        then(eventProducer).should(times(1)).publishEvent(record.make());
        then(outBoxMapper).should(times(1)).updateExternalEventRecord(SentType.SEND_SUCCESS, record.getEventId());
    }

    @Test
    @DisplayName("채널 생성 이벤트 브로커 예외 테스트")
    void register_channel_event_external_exception() {
        ChannelCreatedEvent channelCreatedEvent = new ChannelCreatedEvent(1L, "CREATED_CHANNEL", 1L, 1L, "channel",
                ChannelType.FORUM);
        ExternalEventRecord record = ExternalEventRecord.builder()
                .eventId(1L)
                .type(ExternalEventType.CREATED_CHANNEL)
                .sentType(SentType.INIT)
                .guildId(channelCreatedEvent.getGuildId())
                .channelId(channelCreatedEvent.getChannelId())
                .categoryId(channelCreatedEvent.getCategoryId())
                .channelName(channelCreatedEvent.getChannelName())
                .channelType(ChannelTypeJpaEnum.valueOf(channelCreatedEvent.getChannelType().name()))
                .build();
        given(outBoxMapper.findExternalEventRecord(any(ExternalEventRecord.class))).willReturn(Optional.of(record));
        willThrow(RuntimeException.class).given(eventProducer).publishEvent(record.make());
        willDoNothing().given(outBoxMapper).updateExternalEventRecord(SentType.SEND_FAIL,record.getEventId());

        externalEventHandler.channelCreatedEventAfterHandler(channelCreatedEvent);

        then(outBoxMapper).should(times(1)).findExternalEventRecord(any(ExternalEventRecord.class));
        then(eventProducer).should(times(1)).publishEvent(record.make());
        then(outBoxMapper).should(times(1)).updateExternalEventRecord(SentType.SEND_FAIL, record.getEventId());
    }

    @Test
    @DisplayName("채널 삭제 이벤트 브로커 저장 테스트")
    void delete_channel_event_external_before() {
        ChannelDeletedEvent channelDeletedEvent = new ChannelDeletedEvent(1L, "DELETED_CHANNEL", 1L);
        willDoNothing().given(outBoxMapper).insertExternalEventRecord(argThat(record ->
                record.getType().equals(ExternalEventType.DELETED_CHANNEL) &&
                record.getGuildId().equals(channelDeletedEvent.getGuildId()) &&
                record.getChannelId().equals(channelDeletedEvent.getChannelId()) &&
                record.getSentType().equals(SentType.INIT)));

        externalEventHandler.channelDeletedEventBeforeHandler(channelDeletedEvent);

        then(outBoxMapper).should(times(1)).insertExternalEventRecord(any(ExternalEventRecord.class));
    }

    @Test
    @DisplayName("채널 삭제 이벤트 브로커 발행 테스트")
    void delete_channel_event_external_after() {
        ChannelDeletedEvent channelDeletedEvent = new ChannelDeletedEvent(1L, "DELETED_CHANNEL", 1L);
        ExternalEventRecord record = ExternalEventRecord.builder()
                .eventId(1L)
                .type(ExternalEventType.DELETED_CHANNEL)
                .guildId(channelDeletedEvent.getGuildId())
                .channelId(channelDeletedEvent.getChannelId())
                .sentType(SentType.INIT)
                .build();
        given(outBoxMapper.findExternalEventRecord(any(ExternalEventRecord.class))).willReturn(Optional.of(record));
        willDoNothing().given(eventProducer).publishEvent(record.make());
        willDoNothing().given(outBoxMapper).updateExternalEventRecord(SentType.SEND_SUCCESS,record.getEventId());

        externalEventHandler.channelDeletedEventAfterHandler(channelDeletedEvent);

        then(outBoxMapper).should(times(1)).findExternalEventRecord(any(ExternalEventRecord.class));
        then(eventProducer).should(times(1)).publishEvent(record.make());
        then(outBoxMapper).should(times(1)).updateExternalEventRecord(SentType.SEND_SUCCESS,record.getEventId());
    }

    @Test
    @DisplayName("채널 삭제 이벤트 브로커 예외 테스트")
    void delete_channel_event_external_exception() {
        ChannelDeletedEvent channelDeletedEvent = new ChannelDeletedEvent(1L, "DELETED_CHANNEL", 1L);
        ExternalEventRecord record = ExternalEventRecord.builder()
                .eventId(1L)
                .type(ExternalEventType.DELETED_CHANNEL)
                .guildId(channelDeletedEvent.getGuildId())
                .channelId(channelDeletedEvent.getChannelId())
                .sentType(SentType.INIT)
                .build();
        given(outBoxMapper.findExternalEventRecord(any(ExternalEventRecord.class))).willReturn(Optional.of(record));
        willThrow(RuntimeException.class).given(eventProducer).publishEvent(record.make());
        willDoNothing().given(outBoxMapper).updateExternalEventRecord(SentType.SEND_FAIL,record.getEventId());

        externalEventHandler.channelDeletedEventAfterHandler(channelDeletedEvent);

        then(outBoxMapper).should(times(1)).findExternalEventRecord(any(ExternalEventRecord.class));
        then(eventProducer).should(times(1)).publishEvent(record.make());
        then(outBoxMapper).should(times(1)).updateExternalEventRecord(SentType.SEND_FAIL,record.getEventId());
    }
}