package harmony.communityservice.common.event.handler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;

import harmony.communityservice.common.event.dto.inner.DeleteGuildReadEvent;
import harmony.communityservice.common.event.dto.inner.RegisterGuildReadEvent;
import harmony.communityservice.common.outbox.InnerEventOutBoxMapper;
import harmony.communityservice.common.outbox.InnerEventRecord;
import harmony.communityservice.common.outbox.InnerEventType;
import harmony.communityservice.common.outbox.SentType;
import harmony.communityservice.guild.guild.application.port.in.DeleteGuildReadUseCase;
import harmony.communityservice.guild.guild.application.port.in.RegisterGuildReadCommand;
import harmony.communityservice.guild.guild.application.port.in.RegisterGuildReadUseCase;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GuildReadEventHandlerTest {

    @Mock
    private InnerEventOutBoxMapper outBoxMapper;

    @Mock
    private RegisterGuildReadUseCase registerGuildReadUseCase;

    @Mock
    private DeleteGuildReadUseCase deleteGuildReadUseCase;

    private GuildReadEventHandler guildReadEventHandler;

    @BeforeEach
    void setting() {
        guildReadEventHandler = new GuildReadEventHandler(outBoxMapper, registerGuildReadUseCase,
                deleteGuildReadUseCase);
    }

    @Test
    @DisplayName("길드 읽기 등록 이벤트 저장 테스트")
    void register_guild_read_event_before() {
        RegisterGuildReadEvent registerGuildReadEvent = new RegisterGuildReadEvent(1L, 1L, "GUILD",
                "https://cdn.com/test");
        willDoNothing().given(outBoxMapper).insertInnerEventRecord(argThat(record ->
                record.getGuildId().equals(registerGuildReadEvent.guildId()) &&
                        record.getUserId().equals(registerGuildReadEvent.userId()) &&
                        record.getGuildName().equals(registerGuildReadEvent.name()) &&
                        record.getGuildProfile().equals(registerGuildReadEvent.profile()) &&
                        record.getType().equals(InnerEventType.CREATED_GUILD) &&
                        record.getSentType().equals(SentType.INIT)));

        guildReadEventHandler.guildReadRegisterBeforeHandler(registerGuildReadEvent);

        then(outBoxMapper).should(times(1)).insertInnerEventRecord(any(InnerEventRecord.class));
    }

    @Test
    @DisplayName("길드 읽기 등록 이벤트 발행 테스트")
    void register_guild_read_event_after() {
        RegisterGuildReadEvent registerGuildReadEvent = new RegisterGuildReadEvent(1L, 1L, "GUILD",
                "https://cdn.com/test");
        InnerEventRecord record = InnerEventRecord.builder()
                .guildId(registerGuildReadEvent.guildId())
                .userId(registerGuildReadEvent.userId())
                .guildName(registerGuildReadEvent.name())
                .guildProfile(registerGuildReadEvent.profile())
                .type(InnerEventType.CREATED_GUILD)
                .sentType(SentType.INIT)
                .build();
        given(outBoxMapper.findInnerEventRecord(any(InnerEventRecord.class))).willReturn(Optional.of(record));
        RegisterGuildReadCommand registerGuildReadCommand = RegisterGuildReadCommand.builder()
                .userId(record.getUserId())
                .guildId(record.getGuildId())
                .profile(record.getGuildProfile())
                .name(record.getGuildName())
                .build();
        willDoNothing().given(registerGuildReadUseCase).register(registerGuildReadCommand);
        willDoNothing().given(outBoxMapper).updateInnerEventRecord(SentType.SEND_SUCCESS, record.getEventId());

        guildReadEventHandler.guildReadRegisterAfterHandler(registerGuildReadEvent);

        then(outBoxMapper).should(times(1)).findInnerEventRecord(any(InnerEventRecord.class));
        then(registerGuildReadUseCase).should(times(1)).register(registerGuildReadCommand);
        then(outBoxMapper).should(times(1)).updateInnerEventRecord(SentType.SEND_SUCCESS, record.getEventId());
    }

    @Test
    @DisplayName("길드 읽기 등록 이벤트 예외 테스트")
    void register_guild_read_event_exception() {
        RegisterGuildReadEvent registerGuildReadEvent = new RegisterGuildReadEvent(1L, 1L, "GUILD",
                "https://cdn.com/test");
        InnerEventRecord record = InnerEventRecord.builder()
                .guildId(registerGuildReadEvent.guildId())
                .userId(registerGuildReadEvent.userId())
                .guildName(registerGuildReadEvent.name())
                .guildProfile(registerGuildReadEvent.profile())
                .type(InnerEventType.CREATED_GUILD)
                .sentType(SentType.INIT)
                .build();
        given(outBoxMapper.findInnerEventRecord(any(InnerEventRecord.class))).willReturn(Optional.of(record));
        RegisterGuildReadCommand registerGuildReadCommand = RegisterGuildReadCommand.builder()
                .userId(record.getUserId())
                .guildId(record.getGuildId())
                .profile(record.getGuildProfile())
                .name(record.getGuildName())
                .build();
        willThrow(RuntimeException.class).given(registerGuildReadUseCase).register(registerGuildReadCommand);
        willDoNothing().given(outBoxMapper).updateInnerEventRecord(SentType.SEND_FAIL, record.getEventId());

        guildReadEventHandler.guildReadRegisterAfterHandler(registerGuildReadEvent);

        then(outBoxMapper).should(times(1)).findInnerEventRecord(any(InnerEventRecord.class));
        then(registerGuildReadUseCase).should(times(1)).register(registerGuildReadCommand);
        then(outBoxMapper).should(times(1)).updateInnerEventRecord(SentType.SEND_FAIL, record.getEventId());
    }

    @Test
    @DisplayName("길드 읽기 삭제 이벤트 저장 테스트")
    void delete_guild_read_event_before() {
        DeleteGuildReadEvent deleteGuildReadEvent = new DeleteGuildReadEvent(1L);
        willDoNothing().given(outBoxMapper).insertInnerEventRecord(argThat(record ->
                record.getGuildId().equals(deleteGuildReadEvent.guildId()) &&
                        record.getType().equals(InnerEventType.DELETED_GUILD) &&
                        record.getSentType().equals(SentType.INIT)));

        guildReadEventHandler.guildReadDeleteBeforeHandler(deleteGuildReadEvent);

        then(outBoxMapper).should(times(1)).insertInnerEventRecord(any(InnerEventRecord.class));
    }

    @Test
    @DisplayName("길드 읽기 삭제 이벤트 발행 테스트")
    void delete_guild_read_event_after() {
        DeleteGuildReadEvent deleteGuildReadEvent = new DeleteGuildReadEvent(1L);
        InnerEventRecord record = InnerEventRecord.builder()
                .guildId(deleteGuildReadEvent.guildId())
                .type(InnerEventType.DELETED_GUILD)
                .sentType(SentType.INIT)
                .build();
        given(outBoxMapper.findInnerEventRecord(any(InnerEventRecord.class))).willReturn(Optional.of(record));
        willDoNothing().given(deleteGuildReadUseCase).delete(deleteGuildReadEvent.guildId());
        willDoNothing().given(outBoxMapper).updateInnerEventRecord(SentType.SEND_SUCCESS, record.getEventId());

        guildReadEventHandler.guildReadDeleteAfterHandler(deleteGuildReadEvent);

        then(outBoxMapper).should(times(1)).findInnerEventRecord(any(InnerEventRecord.class));
        then(deleteGuildReadUseCase).should(times(1)).delete(deleteGuildReadEvent.guildId());
        then(outBoxMapper).should(times(1)).updateInnerEventRecord(SentType.SEND_SUCCESS, record.getEventId());
    }

    @Test
    @DisplayName("길드 읽기 삭제 이벤트 예외 테스트")
    void delete_guild_read_event_exception() {
        DeleteGuildReadEvent deleteGuildReadEvent = new DeleteGuildReadEvent(1L);
        InnerEventRecord record = InnerEventRecord.builder()
                .guildId(deleteGuildReadEvent.guildId())
                .type(InnerEventType.DELETED_GUILD)
                .sentType(SentType.INIT)
                .build();
        given(outBoxMapper.findInnerEventRecord(any(InnerEventRecord.class))).willReturn(Optional.of(record));
        willThrow(RuntimeException.class).given(deleteGuildReadUseCase).delete(deleteGuildReadEvent.guildId());
        willDoNothing().given(outBoxMapper).updateInnerEventRecord(SentType.SEND_FAIL, record.getEventId());

        guildReadEventHandler.guildReadDeleteAfterHandler(deleteGuildReadEvent);

        then(outBoxMapper).should(times(1)).findInnerEventRecord(any(InnerEventRecord.class));
        then(deleteGuildReadUseCase).should(times(1)).delete(deleteGuildReadEvent.guildId());
        then(outBoxMapper).should(times(1)).updateInnerEventRecord(SentType.SEND_FAIL, record.getEventId());
    }

}