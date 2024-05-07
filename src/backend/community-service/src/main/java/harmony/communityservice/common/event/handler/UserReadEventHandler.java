package harmony.communityservice.common.event.handler;

import harmony.communityservice.common.event.dto.inner.RegisterUserReadEvent;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.outbox.InnerEventOutBoxMapper;
import harmony.communityservice.common.outbox.InnerEventRecord;
import harmony.communityservice.common.outbox.InnerEventType;
import harmony.communityservice.common.outbox.SentType;
import harmony.communityservice.user.dto.RegisterUserReadRequest;
import harmony.communityservice.user.service.command.UserReadCommandService;
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
public class UserReadEventHandler {

    private final InnerEventOutBoxMapper outBoxMapper;
    private final UserReadCommandService userReadCommandService;

    @TransactionalEventListener(classes = RegisterUserReadEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void userReadRegisterBeforeHandler(RegisterUserReadEvent event) {
        InnerEventRecord record = createUserReadRegisterEvent(event);
        outBoxMapper.insertInnerEventRecord(record);
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = RegisterUserReadEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void userReadRegisterAfterHandler(RegisterUserReadEvent event) {
        InnerEventRecord record = createUserReadRegisterEvent(event);
        InnerEventRecord innerEventRecord = outBoxMapper.findInnerEventRecord(record)
                .orElseThrow(NotFoundDataException::new);
        try {
            userReadCommandService.register(new RegisterUserReadRequest(event.userId(), event.guildId()));
            outBoxMapper.updateInnerEventRecord(SentType.SEND_SUCCESS, innerEventRecord.getEventId());
        } catch (Exception e) {
            outBoxMapper.updateInnerEventRecord(SentType.SEND_FAIL, innerEventRecord.getEventId());
        }
    }

    private InnerEventRecord createUserReadRegisterEvent(RegisterUserReadEvent event) {
        return InnerEventRecord.builder()
                .type(InnerEventType.CREATED_USER)
                .sentType(SentType.INIT)
                .guildId(event.guildId())
                .userId(event.userId())
                .build();
    }
}
