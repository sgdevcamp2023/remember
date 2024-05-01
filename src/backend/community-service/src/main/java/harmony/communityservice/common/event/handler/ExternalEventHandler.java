package harmony.communityservice.common.event.handler;

import harmony.communityservice.common.event.dto.produce.GuildCreatedEvent;
import harmony.communityservice.common.event.dto.produce.GuildDeletedEvent;
import harmony.communityservice.common.event.mapper.ToGuildCreatedEventMapper;
import harmony.communityservice.common.event.mapper.ToGuildDeletedEventMapper;
import harmony.communityservice.common.outbox.EventType;
import harmony.communityservice.common.outbox.ExternalEventRecord;
import harmony.communityservice.common.outbox.OutBoxMapper;
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
        ExternalEventRecord record = ExternalEventRecord.builder()
                .type(EventType.CREATED_CHANNEL)
                .guildId(event.getGuildId())
                .name(event.getName())
                .profile(event.getProfile())
                .build();
        outBoxMapper.insertExternalEventRecord(record);
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(classes = GuildCreatedEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void guildCreatedEventAfterHandler(GuildCreatedEvent event) {
//        producerService.publishGuildCreationEvent(ToGuildCreatedEventMapper.convert(event));
    }



    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(classes = GuildDeletedEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void handler(GuildDeletedEvent event) {
//        producerService.publishGuildDeletionEvent();
    }
}
