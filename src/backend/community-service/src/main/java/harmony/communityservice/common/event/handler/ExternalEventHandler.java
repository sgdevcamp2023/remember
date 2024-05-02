package harmony.communityservice.common.event.handler;

import harmony.communityservice.common.event.dto.produce.GuildCreatedEvent;
import harmony.communityservice.common.event.dto.produce.GuildDeletedEvent;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.outbox.EventType;
import harmony.communityservice.common.outbox.ExternalEventRecord;
import harmony.communityservice.common.outbox.OutBoxMapper;
import harmony.communityservice.common.outbox.SentType;
import harmony.communityservice.common.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class ExternalEventHandler {

    private final ProducerService producerService;
    private final OutBoxMapper outBoxMapper;

    @TransactionalEventListener(classes = GuildCreatedEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void guildCreatedEventBeforeHandler(GuildCreatedEvent event) {
        ExternalEventRecord record = createGuildCreatedEvent(event);
        outBoxMapper.insertExternalEventRecord(record);
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(classes = GuildCreatedEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void guildCreatedEventAfterHandler(GuildCreatedEvent event) {
        ExternalEventRecord record = createGuildCreatedEvent(event);
        ExternalEventRecord externalEventRecord = outBoxMapper.findExternalEventRecord(record)
                .orElseThrow(NotFoundDataException::new);
        try {
            producerService.publishGuildCreationEvent(externalEventRecord.make());
            outBoxMapper.updateExternalEventRecord(SentType.SEND_SUCCESS, externalEventRecord.getEventId());
        } catch (Exception e) {
            outBoxMapper.updateExternalEventRecord(SentType.SEND_FAIL, externalEventRecord.getEventId());
        }

    }

    private ExternalEventRecord createGuildCreatedEvent(GuildCreatedEvent event) {
        return ExternalEventRecord.builder()
                .type(EventType.CREATED_CHANNEL)
                .guildId(event.getGuildId())
                .name(event.getName())
                .profile(event.getProfile())
                .sentType(SentType.INIT)
                .build();
    }


    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(classes = GuildDeletedEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void handler(GuildDeletedEvent event) {
//        producerService.publishGuildDeletionEvent();
    }
}
