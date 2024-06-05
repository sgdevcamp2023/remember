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
import harmony.communityservice.board.emoji.application.port.in.DeleteEmojisUseCase;
import harmony.communityservice.common.event.dto.inner.DeleteEmojiEvent;
import harmony.communityservice.common.event.dto.inner.DeleteEmojisEvent;
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
class EmojiEventHandlerTest {

    @Mock
    private InnerEventOutBoxMapper outBoxMapper;

    @Mock
    private DeleteEmojisUseCase deleteEmojisUseCase;

    private EmojiEventHandler emojiEventHandler;

    @BeforeEach
    void setting() {
        emojiEventHandler = new EmojiEventHandler(outBoxMapper, deleteEmojisUseCase);
    }

    @Test
    @DisplayName("이모지 삭제 이벤트 저장 테스트")
    void delete_emoji_event_before() {
        DeleteEmojiEvent deleteEmojiEvent = new DeleteEmojiEvent(BoardId.make(1L));
        willDoNothing().given(outBoxMapper).insertInnerEventRecord(argThat(record ->
                record.getSentType().equals(SentType.INIT) &&
                        record.getType().equals(InnerEventType.DELETED_EMOJI_IN_BOARD) &&
                        record.getBoardId().equals(deleteEmojiEvent.boardId().getId())));

        emojiEventHandler.emojisDeleteEventBeforeHandler(deleteEmojiEvent);

        then(outBoxMapper).should(times(1)).insertInnerEventRecord(any(InnerEventRecord.class));
    }

    @Test
    @DisplayName("이모지 삭제 이벤트 테스트")
    void delete_emoji_event_after() {
        DeleteEmojiEvent deleteEmojiEvent = new DeleteEmojiEvent(BoardId.make(1L));
        InnerEventRecord record = InnerEventRecord.builder()
                .eventId(1L)
                .type(InnerEventType.DELETED_EMOJI_IN_BOARD)
                .sentType(SentType.INIT)
                .boardId(deleteEmojiEvent.boardId().getId())
                .build();
        given(outBoxMapper.findInnerEventRecord(any(InnerEventRecord.class))).willReturn(Optional.of(record));
        willDoNothing().given(deleteEmojisUseCase).deleteByBoardId(deleteEmojiEvent.boardId());
        willDoNothing().given(outBoxMapper).updateInnerEventRecord(SentType.SEND_SUCCESS, record.getEventId());

        emojiEventHandler.emojisDeleteEventAfterHandler(deleteEmojiEvent);

        then(outBoxMapper).should(times(1)).findInnerEventRecord(any(InnerEventRecord.class));
        then(deleteEmojisUseCase).should(times(1)).deleteByBoardId(deleteEmojiEvent.boardId());
        then(outBoxMapper).should(times(1)).updateInnerEventRecord(SentType.SEND_SUCCESS, record.getEventId());
    }

    @Test
    @DisplayName("이모지 삭제 이벤트 예외 테스트")
    void delete_emoji_event_exception() {
        DeleteEmojiEvent deleteEmojiEvent = new DeleteEmojiEvent(BoardId.make(1L));
        InnerEventRecord record = InnerEventRecord.builder()
                .eventId(1L)
                .type(InnerEventType.DELETED_EMOJI_IN_BOARD)
                .sentType(SentType.INIT)
                .boardId(deleteEmojiEvent.boardId().getId())
                .build();
        given(outBoxMapper.findInnerEventRecord(any(InnerEventRecord.class))).willReturn(Optional.of(record));
        willThrow(RuntimeException.class).given(deleteEmojisUseCase).deleteByBoardId(deleteEmojiEvent.boardId());
        willDoNothing().given(outBoxMapper).updateInnerEventRecord(SentType.SEND_FAIL, record.getEventId());

        emojiEventHandler.emojisDeleteEventAfterHandler(deleteEmojiEvent);

        then(outBoxMapper).should(times(1)).findInnerEventRecord(any(InnerEventRecord.class));
        then(deleteEmojisUseCase).should(times(1)).deleteByBoardId(deleteEmojiEvent.boardId());
        then(outBoxMapper).should(times(1)).updateInnerEventRecord(SentType.SEND_FAIL, record.getEventId());
    }

    @Test
    @DisplayName("이모지 리스트 삭제 이벤트 저장 테스트")
    void delete_emojis_event_before() {
        DeleteEmojisEvent deleteEmojisEvent = new DeleteEmojisEvent(
                List.of(BoardId.make(1L), BoardId.make(2L), BoardId.make(3L)));
        willDoNothing().given(outBoxMapper).insertInnerEventRecords(anyList());

        emojiEventHandler.emojisDeleteEventBeforeHandler(deleteEmojisEvent);

        then(outBoxMapper).should(times(1)).insertInnerEventRecords(anyList());
    }

    @Test
    @DisplayName("이모지 리스트 삭제 이벤트 테스트")
    void delete_emojis_event_after() {
        DeleteEmojisEvent deleteEmojisEvent = new DeleteEmojisEvent(
                List.of(BoardId.make(1L), BoardId.make(2L), BoardId.make(3L)));
        List<InnerEventRecord> records = deleteEmojisEvent.boardIds().stream()
                .map(BoardId::getId)
                .map(boardId ->
                        InnerEventRecord.builder()
                                .eventId(boardId)
                                .sentType(SentType.INIT)
                                .type(InnerEventType.DELETED_EMOJI_IN_BOARDS)
                                .boardId(boardId)
                                .build()
                )
                .toList();
        given(outBoxMapper.findInnerEventRecords(anyList())).willReturn(records);
        willDoNothing().given(deleteEmojisUseCase).deleteByBoardIds(deleteEmojisEvent.boardIds());
        willDoNothing().given(outBoxMapper).updateInnerEventRecords(SentType.SEND_SUCCESS, records);

        emojiEventHandler.emojisDeleteEventAfterHandler(deleteEmojisEvent);

        then(outBoxMapper).should(times(1)).findInnerEventRecords(anyList());
        then(deleteEmojisUseCase).should(times(1)).deleteByBoardIds(deleteEmojisEvent.boardIds());
        then(outBoxMapper).should(times(1)).updateInnerEventRecords(SentType.SEND_SUCCESS, records);
    }

    @Test
    @DisplayName("이모지 리스트 삭제 이벤트 예외 테스트")
    void delete_emojis_event_exception() {
        DeleteEmojisEvent deleteEmojisEvent = new DeleteEmojisEvent(
                List.of(BoardId.make(1L), BoardId.make(2L), BoardId.make(3L)));
        List<InnerEventRecord> records = deleteEmojisEvent.boardIds().stream()
                .map(BoardId::getId)
                .map(boardId ->
                        InnerEventRecord.builder()
                                .eventId(boardId)
                                .sentType(SentType.INIT)
                                .type(InnerEventType.DELETED_EMOJI_IN_BOARDS)
                                .boardId(boardId)
                                .build()
                )
                .toList();
        given(outBoxMapper.findInnerEventRecords(anyList())).willReturn(records);
        willThrow(RuntimeException.class).given(deleteEmojisUseCase).deleteByBoardIds(deleteEmojisEvent.boardIds());
        willDoNothing().given(outBoxMapper).updateInnerEventRecords(SentType.SEND_FAIL, records);

        emojiEventHandler.emojisDeleteEventAfterHandler(deleteEmojisEvent);

        then(outBoxMapper).should(times(1)).findInnerEventRecords(anyList());
        then(deleteEmojisUseCase).should(times(1)).deleteByBoardIds(deleteEmojisEvent.boardIds());
        then(outBoxMapper).should(times(1)).updateInnerEventRecords(SentType.SEND_FAIL, records);
    }
}