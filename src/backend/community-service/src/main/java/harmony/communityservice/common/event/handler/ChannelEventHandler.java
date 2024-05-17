package harmony.communityservice.common.event.handler;

import harmony.communityservice.common.event.dto.inner.DeleteChannelEvent;
import harmony.communityservice.common.event.dto.inner.RegisterChannelEvent;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.outbox.InnerEventOutBoxMapper;
import harmony.communityservice.common.outbox.InnerEventRecord;
import harmony.communityservice.common.outbox.InnerEventType;
import harmony.communityservice.common.outbox.SentType;
import harmony.communityservice.guild.channel.application.port.in.DeleteGuildChannelsUseCase;
import harmony.communityservice.guild.channel.application.port.in.RegisterChannelCommand;
import harmony.communityservice.guild.channel.application.port.in.RegisterChannelUseCase;
import harmony.communityservice.guild.guild.application.service.RegisterChannelCommandMapper;
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
public class ChannelEventHandler {

    private final InnerEventOutBoxMapper outBoxMapper;
    private final RegisterChannelUseCase registerChannelUseCase;
    private final DeleteGuildChannelsUseCase deleteGuildChannelsUseCase;

    @TransactionalEventListener(classes = RegisterChannelEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void channelRegisterEventBeforeHandler(RegisterChannelEvent event) {
        InnerEventRecord record = createChannelRegisterEvent(event);
        outBoxMapper.insertInnerEventRecord(record);
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = RegisterChannelEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void channelRegisterEventAfterHandler(RegisterChannelEvent event) {
        InnerEventRecord record = createChannelRegisterEvent(event);
        InnerEventRecord innerEventRecord = outBoxMapper.findInnerEventRecord(record)
                .orElseThrow(NotFoundDataException::new);
        try {
            RegisterChannelCommand registerChannelCommand = RegisterChannelCommandMapper.convert(innerEventRecord);
            registerChannelUseCase.register(registerChannelCommand);
            outBoxMapper.updateInnerEventRecord(SentType.SEND_SUCCESS, innerEventRecord.getEventId());
        } catch (Exception e) {
            outBoxMapper.updateInnerEventRecord(SentType.SEND_FAIL, innerEventRecord.getEventId());
        }
    }

    @TransactionalEventListener(classes = DeleteChannelEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void channelDeleteEventBeforeHandler(DeleteChannelEvent event) {
        InnerEventRecord record = createChannelDeleteEvent(event);
        outBoxMapper.insertInnerEventRecord(record);
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = DeleteChannelEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void channelDeleteEventAfterHandler(DeleteChannelEvent event) {
        InnerEventRecord record = createChannelDeleteEvent(event);
        InnerEventRecord innerEventRecord = outBoxMapper.findInnerEventRecord(record)
                .orElseThrow(NotFoundDataException::new);
        try {
            deleteGuildChannelsUseCase.deleteByGuildId(innerEventRecord.getGuildId());
            outBoxMapper.updateInnerEventRecord(SentType.SEND_SUCCESS, innerEventRecord.getEventId());
        } catch (Exception e) {
            outBoxMapper.updateInnerEventRecord(SentType.SEND_FAIL, innerEventRecord.getEventId());
        }
    }

    private InnerEventRecord createChannelRegisterEvent(RegisterChannelEvent event) {
        return InnerEventRecord.builder()
                .userId(event.userId())
                .guildId(event.guildId())
                .channelName(event.channelName())
                .channelType(event.type())
                .categoryId(event.categoryId())
                .sentType(SentType.INIT)
                .type(InnerEventType.CREATED_CHANNEL)
                .build();
    }

    private InnerEventRecord createChannelDeleteEvent(DeleteChannelEvent event) {
        return InnerEventRecord.builder()
                .type(InnerEventType.DELETED_CHANNEL)
                .sentType(SentType.INIT)
                .guildId(event.guildId())
                .build();
    }
}
