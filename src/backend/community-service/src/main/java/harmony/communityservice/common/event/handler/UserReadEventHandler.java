package harmony.communityservice.common.event.handler;

import harmony.communityservice.common.event.dto.inner.RegisterUserReadEvent;
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

    private final UserReadCommandService userReadCommandService;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = RegisterUserReadEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void handler(RegisterUserReadEvent event) {
        userReadCommandService.register(new RegisterUserReadRequest(event.userId(), event.guildId()));
    }
}
