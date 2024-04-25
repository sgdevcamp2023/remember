package harmony.communityservice.common.event.handler;

import harmony.communityservice.board.emoji.service.command.EmojiCommandService;
import harmony.communityservice.common.event.dto.inner.DeleteEmojiEvent;
import harmony.communityservice.common.event.dto.inner.DeleteEmojisEvent;
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
public class EmojiEventHandler {

    private final EmojiCommandService emojiCommandService;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = DeleteEmojiEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void handler(DeleteEmojiEvent event) {
        emojiCommandService.deleteListByBoardId(event.boardId());
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = DeleteEmojisEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void handler(DeleteEmojisEvent event) {
        emojiCommandService.deleteListByBoardIds(event.boardIds());
    }
}
