package harmony.communityservice.common.event.handler;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.comment.application.port.in.DeleteCommentsUseCase;
import harmony.communityservice.common.event.dto.inner.DeleteCommentEvent;
import harmony.communityservice.common.event.dto.inner.DeleteCommentsEvent;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.outbox.InnerEventOutBoxMapper;
import harmony.communityservice.common.outbox.InnerEventRecord;
import harmony.communityservice.common.outbox.InnerEventType;
import harmony.communityservice.common.outbox.SentType;
import java.util.List;
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

    private final InnerEventOutBoxMapper outBoxMapper;
    private final DeleteCommentsUseCase deleteCommentsUseCase;

    @TransactionalEventListener(classes = DeleteCommentEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void commentsDeleteEventBeforeHandler(DeleteCommentEvent event) {
        InnerEventRecord record = createCommentsDeleteEvent(event);
        outBoxMapper.insertInnerEventRecord(record);
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = DeleteCommentEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void commentsDeleteEventAfterHandler(DeleteCommentEvent event) {
        InnerEventRecord record = createCommentsDeleteEvent(event);
        InnerEventRecord innerEventRecord = outBoxMapper.findInnerEventRecord(record)
                .orElseThrow(NotFoundDataException::new);
        try {
            deleteCommentsUseCase.deleteByBoardId(BoardId.make(innerEventRecord.getBoardId()));
            outBoxMapper.updateInnerEventRecord(SentType.SEND_SUCCESS, innerEventRecord.getEventId());
        } catch (Exception e) {
            outBoxMapper.updateInnerEventRecord(SentType.SEND_FAIL, innerEventRecord.getEventId());
        }
    }

    @TransactionalEventListener(classes = DeleteCommentsEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void commentsDeleteEventBeforeHandler(DeleteCommentsEvent event) {
        List<InnerEventRecord> records = createCommentsInBoardsDeleteEvent(event);
        if (!records.isEmpty()) {
            outBoxMapper.insertInnerEventRecords(records);
        }
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = DeleteCommentsEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void commentsDeleteEventAfterHandler(DeleteCommentsEvent event) {
        List<InnerEventRecord> records = createCommentsInBoardsDeleteEvent(event);
        List<InnerEventRecord> innerEventRecords = outBoxMapper.findInnerEventRecords(records);
        try {
            deleteCommentsUseCase.deleteByBoardIds(event.boardIds());
            outBoxMapper.updateInnerEventRecords(SentType.SEND_SUCCESS, innerEventRecords);
        } catch (Exception e) {
            outBoxMapper.updateInnerEventRecords(SentType.SEND_FAIL, innerEventRecords);
        }
    }

    private InnerEventRecord createCommentsDeleteEvent(DeleteCommentEvent event) {
        return InnerEventRecord.builder()
                .type(InnerEventType.DELETED_COMMENT_IN_BOARD)
                .sentType(SentType.INIT)
                .boardId(event.boardId().getId())
                .build();
    }

    private List<InnerEventRecord> createCommentsInBoardsDeleteEvent(DeleteCommentsEvent event) {
        return event.boardIds().stream()
                .map(BoardId::getId)
                .map(boardId ->
                        InnerEventRecord.builder()
                                .sentType(SentType.INIT)
                                .type(InnerEventType.DELETED_COMMENT_IN_BOARDS)
                                .boardId(boardId)
                                .build()
                )
                .toList();
    }
}
