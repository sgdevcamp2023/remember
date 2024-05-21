package harmony.communityservice.common.event.handler;

import harmony.communityservice.common.event.dto.inner.DeleteGuildReadEvent;
import harmony.communityservice.common.event.dto.inner.RegisterGuildReadEvent;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.outbox.InnerEventOutBoxMapper;
import harmony.communityservice.common.outbox.InnerEventRecord;
import harmony.communityservice.common.outbox.InnerEventType;
import harmony.communityservice.common.outbox.SentType;
import harmony.communityservice.guild.guild.application.port.in.DeleteGuildReadUseCase;
import harmony.communityservice.guild.guild.application.port.in.RegisterGuildReadCommand;
import harmony.communityservice.guild.guild.application.port.in.RegisterGuildReadUseCase;
import harmony.communityservice.guild.guild.application.service.RegisterGuildReadCommandMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class GuildReadEventHandler {

    private final InnerEventOutBoxMapper outBoxMapper;
    private final RegisterGuildReadUseCase registerGuildReadUseCase;
    private final DeleteGuildReadUseCase deleteGuildReadUseCase;

    private InnerEventRecord createGuildReadRegisterEvent(RegisterGuildReadEvent event) {
        return InnerEventRecord.builder()
                .guildId(event.guildId())
                .userId(event.userId())
                .guildName(event.name())
                .guildProfile(event.profile())
                .type(InnerEventType.CREATED_GUILD)
                .sentType(SentType.INIT)
                .build();
    }

    @TransactionalEventListener(classes = RegisterGuildReadEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void guildReadRegisterBeforeHandler(RegisterGuildReadEvent event) {
        InnerEventRecord record = createGuildReadRegisterEvent(event);
        outBoxMapper.insertInnerEventRecord(record);
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = RegisterGuildReadEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void guildReadRegisterAfterHandler(RegisterGuildReadEvent event) {
        InnerEventRecord record = createGuildReadRegisterEvent(event);
        InnerEventRecord innerEventRecord = outBoxMapper.findInnerEventRecord(record)
                .orElseThrow(NotFoundDataException::new);
        try {
            RegisterGuildReadCommand registerGuildReadCommand = RegisterGuildReadCommandMapper.convert(
                    innerEventRecord);
            registerGuildReadUseCase.register(registerGuildReadCommand);
            outBoxMapper.updateInnerEventRecord(SentType.SEND_SUCCESS, innerEventRecord.getEventId());
        } catch (Exception e) {
            outBoxMapper.updateInnerEventRecord(SentType.SEND_FAIL, innerEventRecord.getEventId());
        }
    }

    @TransactionalEventListener(classes = DeleteGuildReadEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void guildReadDeleteBeforeHandler(DeleteGuildReadEvent event) {
        InnerEventRecord record = createGuildReadDeleteEvent(event);
        outBoxMapper.insertInnerEventRecord(record);
    }

    private InnerEventRecord createGuildReadDeleteEvent(DeleteGuildReadEvent event) {
        return InnerEventRecord.builder()
                .guildId(event.guildId())
                .type(InnerEventType.DELETED_GUILD)
                .sentType(SentType.INIT)
                .build();
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = DeleteGuildReadEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void guildReadDeleteAfterHandler(DeleteGuildReadEvent event) {
        InnerEventRecord record = createGuildReadDeleteEvent(event);
        InnerEventRecord innerEventRecord = outBoxMapper.findInnerEventRecord(record)
                .orElseThrow(NotFoundDataException::new);
        try {
            deleteGuildReadUseCase.delete(innerEventRecord.getGuildId());
            outBoxMapper.updateInnerEventRecord(SentType.SEND_SUCCESS, innerEventRecord.getEventId());
        } catch (Exception e) {
            outBoxMapper.updateInnerEventRecord(SentType.SEND_FAIL, innerEventRecord.getEventId());
        }
    }
}
