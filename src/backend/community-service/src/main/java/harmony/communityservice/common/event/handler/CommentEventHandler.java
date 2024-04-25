package harmony.communityservice.common.event.handler;

import harmony.communityservice.board.comment.service.command.CommentCommandService;
import harmony.communityservice.common.event.dto.inner.DeleteCommentEvent;
import harmony.communityservice.common.event.dto.inner.DeleteCommentsEvent;
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
public class CommentEventHandler {

    private final CommentCommandService commentCommandService;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = DeleteCommentEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void handler(DeleteCommentEvent event) {
        commentCommandService.deleteListByBoardId(event.boardId());
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = DeleteCommentsEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void handler(DeleteCommentsEvent event) {
        commentCommandService.deleteListByBoardIds(event.boardIds());
    }
}
