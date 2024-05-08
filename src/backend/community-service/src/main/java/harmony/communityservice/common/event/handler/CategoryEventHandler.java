package harmony.communityservice.common.event.handler;

import harmony.communityservice.common.event.dto.inner.DeleteCategoryEvent;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.outbox.InnerEventOutBoxMapper;
import harmony.communityservice.common.outbox.InnerEventRecord;
import harmony.communityservice.common.outbox.InnerEventType;
import harmony.communityservice.common.outbox.SentType;
import harmony.communityservice.guild.category.service.command.CategoryCommandService;
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
public class CategoryEventHandler {

    private final InnerEventOutBoxMapper outBoxMapper;
    private final CategoryCommandService categoryCommandService;

    @TransactionalEventListener(classes = DeleteCategoryEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void categoryDeleteEventBeforeHandler(DeleteCategoryEvent event) {
        InnerEventRecord record = createCategoryDeleteEvent(event);
        outBoxMapper.insertInnerEventRecord(record);
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = DeleteCategoryEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void categoryDeleteEventAfterHandler(DeleteCategoryEvent event) {
        InnerEventRecord record = createCategoryDeleteEvent(event);
        InnerEventRecord innerEventRecord = outBoxMapper.findInnerEventRecord(record)
                .orElseThrow(NotFoundDataException::new);
        try {
            categoryCommandService.deleteByGuildId(innerEventRecord.getGuildId());
            outBoxMapper.updateInnerEventRecord(SentType.SEND_SUCCESS, innerEventRecord.getEventId());
        } catch (Exception e) {
            outBoxMapper.updateInnerEventRecord(SentType.SEND_FAIL, innerEventRecord.getEventId());
        }
    }

    private InnerEventRecord createCategoryDeleteEvent(DeleteCategoryEvent event) {
        return InnerEventRecord.builder()
                .type(InnerEventType.DELETED_CATEGORY)
                .sentType(SentType.INIT)
                .guildId(event.guildId())
                .build();
    }
}
