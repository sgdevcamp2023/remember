package harmony.communityservice.common.event.handler;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import harmony.communityservice.common.event.dto.inner.DeleteChannelEvent;
import harmony.communityservice.common.event.dto.inner.RegisterChannelEvent;
import harmony.communityservice.common.outbox.InnerEventOutBoxMapper;
import harmony.communityservice.common.outbox.InnerEventRecord;
import harmony.communityservice.common.outbox.InnerEventType;
import harmony.communityservice.common.outbox.SentType;
import harmony.communityservice.guild.channel.application.port.in.DeleteGuildChannelsUseCase;
import harmony.communityservice.guild.channel.application.port.in.RegisterChannelCommand;
import harmony.communityservice.guild.channel.application.port.in.RegisterChannelUseCase;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChannelEventHandlerTest {

    @Mock
    private InnerEventOutBoxMapper outBoxMapper;
    @Mock
    private RegisterChannelUseCase registerChannelUseCase;
    @Mock
    private DeleteGuildChannelsUseCase deleteGuildChannelsUseCase;
    private ChannelEventHandler channelEventHandler;

    @BeforeEach
    void setting() {
        channelEventHandler = new ChannelEventHandler(outBoxMapper, registerChannelUseCase, deleteGuildChannelsUseCase);
    }

    @Test
    @DisplayName("채널 등록 이벤트 저장 테스트")
    void register_channel_event_before() {
        RegisterChannelEvent registerChannelEvent = new RegisterChannelEvent(1L, "CHANNEL", 1L, 1L, "FORUM");
        willDoNothing().given(outBoxMapper).insertInnerEventRecord(argThat(record ->
                record.getGuildId().equals(registerChannelEvent.guildId()) &&
                        record.getCategoryId().equals(registerChannelEvent.categoryId()) &&
                        record.getUserId().equals(registerChannelEvent.userId()) &&
                        record.getChannelName().equals(registerChannelEvent.channelName()) &&
                        record.getChannelType().equals(registerChannelEvent.type()) &&
                        record.getSentType().equals(SentType.INIT) &&
                        record.getType().equals(InnerEventType.CREATED_CHANNEL)
        ));

        channelEventHandler.channelRegisterEventBeforeHandler(registerChannelEvent);

        then(outBoxMapper).should(times(1)).insertInnerEventRecord(any(InnerEventRecord.class));
    }

    @Test
    @DisplayName("채널 등록 이벤트 테스트")
    void register_channel_event_after() {
        RegisterChannelEvent registerChannelEvent = new RegisterChannelEvent(1L, "CHANNEL", 1L, 1L, "FORUM");
        InnerEventRecord record = InnerEventRecord.builder()
                .eventId(1L)
                .userId(registerChannelEvent.userId())
                .guildId(registerChannelEvent.guildId())
                .channelName(registerChannelEvent.channelName())
                .channelType(registerChannelEvent.type())
                .categoryId(registerChannelEvent.categoryId())
                .sentType(SentType.INIT)
                .type(InnerEventType.CREATED_CHANNEL)
                .build();
        RegisterChannelCommand registerChannelCommand = new RegisterChannelCommand(record.getGuildId(),
                record.getChannelName(), record.getUserId(),
                record.getCategoryId(),
                record.getChannelType());
        given(outBoxMapper.findInnerEventRecord(any(InnerEventRecord.class))).willReturn(Optional.of(record));
        willDoNothing().given(registerChannelUseCase).register(registerChannelCommand);
        willDoNothing().given(outBoxMapper).updateInnerEventRecord(SentType.SEND_SUCCESS, 1L);

        channelEventHandler.channelRegisterEventAfterHandler(registerChannelEvent);

        then(outBoxMapper).should(times(1)).findInnerEventRecord(any(InnerEventRecord.class));
        then(registerChannelUseCase).should(times(1)).register(registerChannelCommand);
        then(outBoxMapper).should(times(1)).updateInnerEventRecord(SentType.SEND_SUCCESS, 1L);
    }

    @Test
    @DisplayName("채널 등록 이벤트 예외 테스트")
    void register_channel_event_exception() {
        RegisterChannelEvent registerChannelEvent = new RegisterChannelEvent(1L, "CHANNEL", 1L, 1L, "FORUM");
        InnerEventRecord record = InnerEventRecord.builder()
                .eventId(1L)
                .userId(registerChannelEvent.userId())
                .guildId(registerChannelEvent.guildId())
                .channelName(registerChannelEvent.channelName())
                .channelType(registerChannelEvent.type())
                .categoryId(registerChannelEvent.categoryId())
                .sentType(SentType.INIT)
                .type(InnerEventType.CREATED_CHANNEL)
                .build();
        RegisterChannelCommand registerChannelCommand = new RegisterChannelCommand(record.getGuildId(),
                record.getChannelName(), record.getUserId(),
                record.getCategoryId(),
                record.getChannelType());
        given(outBoxMapper.findInnerEventRecord(any(InnerEventRecord.class))).willReturn(Optional.of(record));
        willDoNothing().given(registerChannelUseCase).register(registerChannelCommand);
        willDoNothing().given(outBoxMapper).updateInnerEventRecord(SentType.SEND_FAIL, 1L);

        channelEventHandler.channelRegisterEventAfterHandler(registerChannelEvent);

        then(outBoxMapper).should(times(1)).findInnerEventRecord(any(InnerEventRecord.class));
        then(registerChannelUseCase).should(times(1)).register(registerChannelCommand);
        then(outBoxMapper).should(times(1)).updateInnerEventRecord(SentType.SEND_FAIL, 1L);
        assertNotNull(record.getCreatedAt());
    }

    @Test
    @DisplayName("채널 삭제 이벤트 저장 테스트")
    void delete_channel_event_before() {
        DeleteChannelEvent deleteChannelEvent = new DeleteChannelEvent(1L);
        willDoNothing().given(outBoxMapper).insertInnerEventRecord(argThat(record ->
                record.getGuildId().equals(deleteChannelEvent.guildId()) &&
                        record.getSentType().equals(SentType.INIT) &&
                        record.getType().equals(InnerEventType.DELETED_CHANNEL)));

        channelEventHandler.channelDeleteEventBeforeHandler(deleteChannelEvent);

        then(outBoxMapper).should(times(1)).insertInnerEventRecord(any(InnerEventRecord.class));
    }

    @Test
    @DisplayName("채널 삭제 이벤트 테스트")
    void delete_channel_event_after() {
        DeleteChannelEvent deleteChannelEvent = new DeleteChannelEvent(1L);
        InnerEventRecord record = InnerEventRecord.builder()
                .eventId(1L)
                .type(InnerEventType.DELETED_CHANNEL)
                .sentType(SentType.INIT)
                .guildId(deleteChannelEvent.guildId())
                .build();
        given(outBoxMapper.findInnerEventRecord(any(InnerEventRecord.class))).willReturn(Optional.of(record));
        willDoNothing().given(deleteGuildChannelsUseCase).deleteByGuildId(deleteChannelEvent.guildId());
        willDoNothing().given(outBoxMapper).updateInnerEventRecord(SentType.SEND_SUCCESS, record.getEventId());

        channelEventHandler.channelDeleteEventAfterHandler(deleteChannelEvent);

        then(outBoxMapper).should(times(1)).findInnerEventRecord(any(InnerEventRecord.class));
        then(deleteGuildChannelsUseCase).should(times(1)).deleteByGuildId(deleteChannelEvent.guildId());
        then(outBoxMapper).should(times(1)).updateInnerEventRecord(SentType.SEND_SUCCESS, record.getEventId());
    }

    @Test
    @DisplayName("채널 삭제 이벤트 예외 테스트")
    void delete_channel_event_exception() {
        DeleteChannelEvent deleteChannelEvent = new DeleteChannelEvent(1L);
        InnerEventRecord record = InnerEventRecord.builder()
                .eventId(1L)
                .type(InnerEventType.DELETED_CHANNEL)
                .sentType(SentType.INIT)
                .guildId(deleteChannelEvent.guildId())
                .build();
        given(outBoxMapper.findInnerEventRecord(any(InnerEventRecord.class))).willReturn(Optional.of(record));
        willDoNothing().given(deleteGuildChannelsUseCase).deleteByGuildId(deleteChannelEvent.guildId());
        willDoNothing().given(outBoxMapper).updateInnerEventRecord(SentType.SEND_FAIL, record.getEventId());

        channelEventHandler.channelDeleteEventAfterHandler(deleteChannelEvent);

        then(outBoxMapper).should(times(1)).findInnerEventRecord(any(InnerEventRecord.class));
        then(deleteGuildChannelsUseCase).should(times(1)).deleteByGuildId(deleteChannelEvent.guildId());
        then(outBoxMapper).should(times(1)).updateInnerEventRecord(SentType.SEND_FAIL, record.getEventId());
    }
}