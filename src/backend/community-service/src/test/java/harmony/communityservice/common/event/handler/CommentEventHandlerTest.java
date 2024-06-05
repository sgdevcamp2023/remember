package harmony.communityservice.common.event.handler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.comment.application.port.in.DeleteCommentsUseCase;
import harmony.communityservice.common.event.dto.inner.DeleteCommentEvent;
import harmony.communityservice.common.event.dto.inner.DeleteCommentsEvent;
import harmony.communityservice.common.outbox.InnerEventOutBoxMapper;
import harmony.communityservice.common.outbox.InnerEventRecord;
import harmony.communityservice.common.outbox.InnerEventType;
import harmony.communityservice.common.outbox.SentType;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentEventHandlerTest {

    @Mock
    private InnerEventOutBoxMapper outBoxMapper;

    @Mock
    private DeleteCommentsUseCase deleteCommentsUseCase;

    private CommentEventHandler commentEventHandler;

    @BeforeEach
    void setting() {
        commentEventHandler = new CommentEventHandler(outBoxMapper, deleteCommentsUseCase);
    }

    @Test
    @DisplayName("댓글 삭제 이벤트 저장 테스트")
    void delete_comment_event_before() {
        DeleteCommentEvent deleteCommentEvent = new DeleteCommentEvent(BoardId.make(1L));
        InnerEventRecord.builder()
                .type(InnerEventType.DELETED_COMMENT_IN_BOARD)
                .sentType(SentType.INIT)
                .boardId(deleteCommentEvent.boardId().getId())
                .build();
        willDoNothing().given(outBoxMapper).insertInnerEventRecord(argThat(record ->
                record.getBoardId().equals(deleteCommentEvent.boardId().getId()) &&
                        record.getType().equals(InnerEventType.DELETED_COMMENT_IN_BOARD) &&
                        record.getSentType().equals(SentType.INIT)));

        commentEventHandler.commentsDeleteEventBeforeHandler(deleteCommentEvent);

        then(outBoxMapper).should(times(1)).insertInnerEventRecord(any(InnerEventRecord.class));
    }

    @Test
    @DisplayName("댓글 삭제 이벤트 테스트")
    void delete_comment_event_after() {
        DeleteCommentEvent deleteCommentEvent = new DeleteCommentEvent(BoardId.make(1L));
        InnerEventRecord record = InnerEventRecord.builder()
                .eventId(1L)
                .type(InnerEventType.DELETED_COMMENT_IN_BOARD)
                .sentType(SentType.INIT)
                .boardId(deleteCommentEvent.boardId().getId())
                .build();
        given(outBoxMapper.findInnerEventRecord(any(InnerEventRecord.class))).willReturn(Optional.of(record));
        willDoNothing().given(deleteCommentsUseCase).deleteByBoardId(deleteCommentEvent.boardId());
        willDoNothing().given(outBoxMapper).updateInnerEventRecord(SentType.SEND_SUCCESS, 1L);

        commentEventHandler.commentsDeleteEventAfterHandler(deleteCommentEvent);

        then(outBoxMapper).should(times(1)).findInnerEventRecord(any(InnerEventRecord.class));
        then(deleteCommentsUseCase).should(times(1)).deleteByBoardId(deleteCommentEvent.boardId());
        then(outBoxMapper).should(times(1)).updateInnerEventRecord(SentType.SEND_SUCCESS, 1L);
    }

    @Test
    @DisplayName("댓글 삭제 이벤트 예외 테스트")
    void delete_comment_event_exception() {
        DeleteCommentEvent deleteCommentEvent = new DeleteCommentEvent(BoardId.make(1L));
        InnerEventRecord record = InnerEventRecord.builder()
                .eventId(1L)
                .type(InnerEventType.DELETED_COMMENT_IN_BOARD)
                .sentType(SentType.INIT)
                .boardId(deleteCommentEvent.boardId().getId())
                .build();
        given(outBoxMapper.findInnerEventRecord(any(InnerEventRecord.class))).willReturn(Optional.of(record));
        willThrow(RuntimeException.class).given(deleteCommentsUseCase).deleteByBoardId(deleteCommentEvent.boardId());
        willDoNothing().given(outBoxMapper).updateInnerEventRecord(SentType.SEND_FAIL, 1L);

        commentEventHandler.commentsDeleteEventAfterHandler(deleteCommentEvent);

        then(outBoxMapper).should(times(1)).findInnerEventRecord(any(InnerEventRecord.class));
        then(deleteCommentsUseCase).should(times(1)).deleteByBoardId(deleteCommentEvent.boardId());
        then(outBoxMapper).should(times(1)).updateInnerEventRecord(SentType.SEND_FAIL, 1L);
    }

    @Test
    @DisplayName("길드 내 댓글 삭제 이벤트 저장 테스트")
    void delete_comments_guild_event_before() {
        DeleteCommentsEvent deleteCommentsEvent = new DeleteCommentsEvent(
                List.of(BoardId.make(1L), BoardId.make(2L), BoardId.make(3L)));
        willDoNothing().given(outBoxMapper).insertInnerEventRecords(anyList());

        commentEventHandler.commentsDeleteEventBeforeHandler(deleteCommentsEvent);

        then(outBoxMapper).should(times(1)).insertInnerEventRecords(anyList());
    }

    @Test
    @DisplayName("길드 내 댓글 삭제 이벤트 테스트")
    void delete_comments_guild_event_after() {
        DeleteCommentsEvent deleteCommentsEvent = new DeleteCommentsEvent(
                List.of(BoardId.make(1L), BoardId.make(2L), BoardId.make(3L)));
        List<InnerEventRecord> records = deleteCommentsEvent.boardIds().stream()
                .map(BoardId::getId)
                .map(boardId ->
                        InnerEventRecord.builder()
                                .eventId(boardId)
                                .sentType(SentType.INIT)
                                .type(InnerEventType.DELETED_COMMENT_IN_BOARDS)
                                .boardId(boardId)
                                .build()
                )
                .toList();
        given(outBoxMapper.findInnerEventRecords(anyList())).willReturn(records);
        willDoNothing().given(deleteCommentsUseCase).deleteByBoardIds(deleteCommentsEvent.boardIds());
        willDoNothing().given(outBoxMapper).updateInnerEventRecords(SentType.SEND_SUCCESS, records);

        commentEventHandler.commentsDeleteEventAfterHandler(deleteCommentsEvent);

        then(outBoxMapper).should(times(1)).findInnerEventRecords(anyList());
        then(deleteCommentsUseCase).should(times(1)).deleteByBoardIds(deleteCommentsEvent.boardIds());
        then(outBoxMapper).should(times(1)).updateInnerEventRecords(SentType.SEND_SUCCESS, records);
    }

    @Test
    @DisplayName("길드 내 댓글 삭제 이벤트 예외 테스트")
    void delete_comments_guild_event_exception() {
        DeleteCommentsEvent deleteCommentsEvent = new DeleteCommentsEvent(
                List.of(BoardId.make(1L), BoardId.make(2L), BoardId.make(3L)));
        List<InnerEventRecord> records = deleteCommentsEvent.boardIds().stream()
                .map(BoardId::getId)
                .map(boardId ->
                        InnerEventRecord.builder()
                                .eventId(boardId)
                                .sentType(SentType.INIT)
                                .type(InnerEventType.DELETED_COMMENT_IN_BOARDS)
                                .boardId(boardId)
                                .build()
                )
                .toList();
        given(outBoxMapper.findInnerEventRecords(anyList())).willReturn(records);
        willThrow(RuntimeException.class).given(deleteCommentsUseCase).deleteByBoardIds(deleteCommentsEvent.boardIds());
        willDoNothing().given(outBoxMapper).updateInnerEventRecords(SentType.SEND_FAIL, records);

        commentEventHandler.commentsDeleteEventAfterHandler(deleteCommentsEvent);

        then(outBoxMapper).should(times(1)).findInnerEventRecords(anyList());
        then(deleteCommentsUseCase).should(times(1)).deleteByBoardIds(deleteCommentsEvent.boardIds());
        then(outBoxMapper).should(times(1)).updateInnerEventRecords(SentType.SEND_FAIL, records);
    }
}