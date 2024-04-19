package harmony.communityservice.common.event.handler;

import harmony.communityservice.common.event.dto.ChannelCreatedEvent;
import harmony.communityservice.common.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class ChannelCreatedEventHandler {

    private final ProducerService producerService;

    @Async
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = ChannelCreatedEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void handler(ChannelCreatedEvent event) {
        producerService.publishChannelCreationEvent(event);
    }
}
