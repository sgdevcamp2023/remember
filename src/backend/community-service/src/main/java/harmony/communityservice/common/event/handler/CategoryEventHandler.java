package harmony.communityservice.common.event.handler;

import harmony.communityservice.common.event.dto.inner.DeleteCategoryEvent;
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

    private final CategoryCommandService categoryCommandService;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = DeleteCategoryEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void handler(DeleteCategoryEvent event) {
        categoryCommandService.deleteByGuildId(event.guildId());
    }
}
