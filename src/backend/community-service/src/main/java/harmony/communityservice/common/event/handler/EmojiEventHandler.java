package harmony.communityservice.common.event.handler;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.emoji.application.port.in.DeleteEmojisUseCase;
import harmony.communityservice.board.emoji.application.port.out.DeleteEmojisPort;
import harmony.communityservice.common.event.dto.inner.DeleteEmojiEvent;
import harmony.communityservice.common.event.dto.inner.DeleteEmojisEvent;
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
public class EmojiEventHandler {

    private final InnerEventOutBoxMapper outBoxMapper;
    private final DeleteEmojisUseCase deleteEmojisUseCase;

    @TransactionalEventListener(classes = DeleteEmojiEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void emojisDeleteEventBeforeHandler(DeleteEmojiEvent event) {
        InnerEventRecord record = createEmojisDeleteEvent(event);
        outBoxMapper.insertInnerEventRecord(record);
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = DeleteEmojiEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void emojisDeleteEventAfterHandler(DeleteEmojiEvent event) {
        InnerEventRecord record = createEmojisDeleteEvent(event);
        InnerEventRecord innerEventRecord = outBoxMapper.findInnerEventRecord(record)
                .orElseThrow(NotFoundDataException::new);
        try {
            deleteEmojisUseCase.deleteByBoardId(BoardId.make(innerEventRecord.getBoardId()));
            outBoxMapper.updateInnerEventRecord(SentType.SEND_SUCCESS, innerEventRecord.getEventId());
        } catch (Exception e) {
            outBoxMapper.updateInnerEventRecord(SentType.SEND_FAIL, innerEventRecord.getEventId());
        }
    }

    @TransactionalEventListener(classes = DeleteEmojisEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void emojisDeleteEventBeforeHandler(DeleteEmojisEvent event) {
        List<InnerEventRecord> records = createEmojisInBoardsDeleteEvent(event);
        if (!records.isEmpty()) {
            outBoxMapper.insertInnerEventRecords(records);
        }
    }


    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = DeleteEmojisEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void handler(DeleteEmojisEvent event) {
        List<InnerEventRecord> records = createEmojisInBoardsDeleteEvent(event);
        List<InnerEventRecord> innerEventRecords = outBoxMapper.findInnerEventRecords(records);
        try {
            deleteEmojisUseCase.deleteByBoardIds(event.boardIds());
            outBoxMapper.updateInnerEventRecords(SentType.SEND_SUCCESS, innerEventRecords);
        } catch (Exception e) {
            outBoxMapper.updateInnerEventRecords(SentType.SEND_FAIL, innerEventRecords);
        }
    }

    private InnerEventRecord createEmojisDeleteEvent(DeleteEmojiEvent event) {
        return InnerEventRecord.builder()
                .type(InnerEventType.DELETED_EMOJI_IN_BOARD)
                .sentType(SentType.INIT)
                .boardId(event.boardId().getId())
                .build();
    }

    private List<InnerEventRecord> createEmojisInBoardsDeleteEvent(DeleteEmojisEvent event) {
        return event.boardIds().stream()
                .map(BoardId::getId)
                .map(boardId ->
                        InnerEventRecord.builder()
                                .sentType(SentType.INIT)
                                .type(InnerEventType.DELETED_EMOJI_IN_BOARDS)
                                .boardId(boardId)
                                .build()
                )
                .toList();
    }
}
