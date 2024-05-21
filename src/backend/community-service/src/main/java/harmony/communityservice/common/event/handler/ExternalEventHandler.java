package harmony.communityservice.common.event.handler;

import harmony.communityservice.common.event.dto.produce.ChannelCreatedEvent;
import harmony.communityservice.common.event.dto.produce.ChannelDeletedEvent;
import harmony.communityservice.common.event.dto.produce.GuildCreatedEvent;
import harmony.communityservice.common.event.dto.produce.GuildDeletedEvent;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.outbox.ExternalEventOutBoxMapper;
import harmony.communityservice.common.outbox.ExternalEventRecord;
import harmony.communityservice.common.outbox.ExternalEventType;
import harmony.communityservice.common.outbox.SentType;
import harmony.communityservice.common.service.EventProducer;
import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelTypeJpaEnum;
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
public class ExternalEventHandler {

    private final EventProducer producerService;
    private final ExternalEventOutBoxMapper outBoxMapper;

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
        publishExternalEvent(record);

    }


    @TransactionalEventListener(classes = GuildDeletedEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void guildDeletedEventBeforeHandler(GuildDeletedEvent event) {
        ExternalEventRecord record = createGuildDeletedEvent(event);
        outBoxMapper.insertExternalEventRecord(record);
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(classes = GuildDeletedEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void guildDeletedEventAfterHandler(GuildDeletedEvent event) {
        ExternalEventRecord record = createGuildDeletedEvent(event);
        publishExternalEvent(record);
    }

    @TransactionalEventListener(classes = ChannelCreatedEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void channelCreatedEventBeforeHandler(ChannelCreatedEvent event) {
        ExternalEventRecord record = createChannelCreatedEvent(event);
        outBoxMapper.insertExternalEventRecord(record);
    }


    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(classes = ChannelCreatedEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void channelCreatedEventAfterHandler(ChannelCreatedEvent event) {
        ExternalEventRecord record = createChannelCreatedEvent(event);
        publishExternalEvent(record);
    }

    @TransactionalEventListener(classes = ChannelDeletedEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void channelDeletedEventBeforeHandler(ChannelDeletedEvent event) {
        ExternalEventRecord record = createChannelDeletedEvent(event);
        outBoxMapper.insertExternalEventRecord(record);
    }


    @Async
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = ChannelDeletedEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void channelDeletedEventAfterHandler(ChannelDeletedEvent event) {
        ExternalEventRecord record = createChannelDeletedEvent(event);
        publishExternalEvent(record);
    }

    private void publishExternalEvent(ExternalEventRecord record) {
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
                .type(ExternalEventType.CREATED_GUILD)
                .guildId(event.getGuildId())
                .name(event.getName())
                .profile(event.getProfile())
                .sentType(SentType.INIT)
                .build();
    }

    private ExternalEventRecord createGuildDeletedEvent(GuildDeletedEvent event) {
        return ExternalEventRecord.builder()
                .type(ExternalEventType.DELETED_GUILD)
                .guildId(event.getGuildId())
                .sentType(SentType.INIT)
                .build();
    }

    private ExternalEventRecord createChannelCreatedEvent(ChannelCreatedEvent event) {
        return ExternalEventRecord.builder()
                .type(ExternalEventType.CREATED_CHANNEL)
                .sentType(SentType.INIT)
                .guildId(event.getGuildId())
                .channelId(event.getChannelId())
                .categoryId(event.getCategoryId())
                .channelName(event.getChannelName())
                .channelType(ChannelTypeJpaEnum.valueOf(event.getChannelType().name()))
                .build();
    }

    private ExternalEventRecord createChannelDeletedEvent(ChannelDeletedEvent event) {
        return ExternalEventRecord.builder()
                .type(ExternalEventType.DELETED_CHANNEL)
                .guildId(event.getGuildId())
                .channelId(event.getChannelId())
                .build();
    }
}
