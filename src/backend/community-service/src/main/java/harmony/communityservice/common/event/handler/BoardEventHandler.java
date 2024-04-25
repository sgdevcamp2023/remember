package harmony.communityservice.common.event.handler;

import harmony.communityservice.board.board.service.command.BoardCommandService;
import harmony.communityservice.common.event.dto.inner.DeleteBoardsEvent;
import harmony.communityservice.common.event.dto.inner.DeleteBoardsInGuildEvent;
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
public class BoardEventHandler {

    private final BoardCommandService boardCommandService;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = DeleteBoardsEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void handler(DeleteBoardsEvent event) {
        boardCommandService.deleteAllInChannelId(event.channelId());
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = DeleteBoardsInGuildEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void handler(DeleteBoardsInGuildEvent event) {
        boardCommandService.deleteAllInChannelIds(event.channelIds());
    }
}
