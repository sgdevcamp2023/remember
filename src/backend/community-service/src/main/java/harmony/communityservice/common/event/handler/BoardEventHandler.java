package harmony.communityservice.common.event.handler;

import harmony.communityservice.board.board.application.port.in.DeleteChannelBoardsUseCase;
import harmony.communityservice.board.board.application.port.in.DeleteChannelsBoardsUseCase;
import harmony.communityservice.common.event.dto.inner.DeleteBoardsEvent;
import harmony.communityservice.common.event.dto.inner.DeleteBoardsInGuildEvent;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.outbox.InnerEventOutBoxMapper;
import harmony.communityservice.common.outbox.InnerEventRecord;
import harmony.communityservice.common.outbox.InnerEventType;
import harmony.communityservice.common.outbox.SentType;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
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
public class BoardEventHandler {

    private final InnerEventOutBoxMapper outBoxMapper;
    private final DeleteChannelsBoardsUseCase deleteChannelsBoardsUseCase;
    private final DeleteChannelBoardsUseCase deleteChannelBoardsUseCase;

    @TransactionalEventListener(classes = DeleteBoardsEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void boardsDeleteEventBeforeHandler(DeleteBoardsEvent event) {
        InnerEventRecord record = createDeleteBoardsEvent(event);
        outBoxMapper.insertInnerEventRecord(record);
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = DeleteBoardsEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void boardsDeleteEventAfterHandler(DeleteBoardsEvent event) {
        InnerEventRecord record = createDeleteBoardsEvent(event);
        InnerEventRecord innerEventRecord = outBoxMapper.findInnerEventRecord(record)
                .orElseThrow(NotFoundDataException::new);
        try {
            deleteChannelBoardsUseCase.deleteChannelBoards(innerEventRecord.getChannelId());
            outBoxMapper.updateInnerEventRecord(SentType.SEND_SUCCESS, innerEventRecord.getEventId());
        } catch (Exception e) {
            outBoxMapper.updateInnerEventRecord(SentType.SEND_FAIL, innerEventRecord.getEventId());
        }
    }

    @TransactionalEventListener(classes = DeleteBoardsInGuildEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void boardsDeleteInGuildEventBeforeHandler(DeleteBoardsInGuildEvent event) {
        List<InnerEventRecord> records = createBoardsDeleteInGuildEvent(event);
        outBoxMapper.insertInnerEventRecords(records);
    }


    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = DeleteBoardsInGuildEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void handler(DeleteBoardsInGuildEvent event) {
        List<InnerEventRecord> records = createBoardsDeleteInGuildEvent(event);
        List<InnerEventRecord> innerEventRecords = outBoxMapper.findInnerEventRecords(records);
        try {
            deleteChannelsBoardsUseCase.deleteInChannels(event.channelIds());
            outBoxMapper.updateInnerEventRecords(SentType.SEND_SUCCESS, innerEventRecords);
        } catch (Exception e) {
            outBoxMapper.updateInnerEventRecords(SentType.SEND_FAIL, innerEventRecords);
        }
    }

    private InnerEventRecord createDeleteBoardsEvent(DeleteBoardsEvent event) {
        return InnerEventRecord.builder()
                .channelId(event.channelId())
                .userId(event.userId())
                .type(InnerEventType.DELETED_BOARD_IN_CHANNEL)
                .sentType(SentType.INIT)
                .build();
    }

    private List<InnerEventRecord> createBoardsDeleteInGuildEvent(DeleteBoardsInGuildEvent event) {
        return event.channelIds().stream()
                .map(ChannelId::getId)
                .map(channelId ->
                        InnerEventRecord.builder()
                                .type(InnerEventType.DELETED_BOARD_IN_CHANNELS)
                                .sentType(SentType.INIT)
                                .channelId(channelId)
                                .build()
                )
                .toList();
    }
}
