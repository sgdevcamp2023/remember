package harmony.communityservice.common.event.handler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;

import harmony.communityservice.board.board.application.port.in.DeleteChannelBoardsUseCase;
import harmony.communityservice.board.board.application.port.in.DeleteChannelsBoardsUseCase;
import harmony.communityservice.common.event.dto.inner.DeleteBoardsEvent;
import harmony.communityservice.common.event.dto.inner.DeleteBoardsInGuildEvent;
import harmony.communityservice.common.outbox.InnerEventOutBoxMapper;
import harmony.communityservice.common.outbox.InnerEventRecord;
import harmony.communityservice.common.outbox.InnerEventType;
import harmony.communityservice.common.outbox.SentType;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BoardEventHandlerTest {

    @Mock
    private InnerEventOutBoxMapper innerEventOutBoxMapper;

    @Mock
    private DeleteChannelsBoardsUseCase deleteChannelsBoardsUseCase;

    @Mock
    private DeleteChannelBoardsUseCase deleteChannelBoardsUseCase;

    private BoardEventHandler boardEventHandler;

    @BeforeEach
    void setting() {
        boardEventHandler = new BoardEventHandler(innerEventOutBoxMapper, deleteChannelsBoardsUseCase,
                deleteChannelBoardsUseCase);
    }

    @Test
    @DisplayName("게시글 리스트 삭제 이벤트 저장 테스트")
    void delete_boards_event_before() {
        DeleteBoardsEvent event = new DeleteBoardsEvent(1L, 1L);
        willDoNothing().given(innerEventOutBoxMapper).insertInnerEventRecord(argThat(record ->
                record.getChannelId().equals(event.channelId()) &&
                        record.getUserId().equals(event.userId()) &&
                        record.getType().equals(InnerEventType.DELETED_BOARD_IN_CHANNEL) &&
                        record.getSentType().equals(SentType.INIT)
        ));

        boardEventHandler.boardsDeleteEventBeforeHandler(new DeleteBoardsEvent(1L, 1L));

        then(innerEventOutBoxMapper).should(times(1)).insertInnerEventRecord(any(InnerEventRecord.class));
    }

    @Test
    @DisplayName("게시글 리스트 삭제 이벤트 테스트")
    void delete_boards_event_after() {
        InnerEventRecord innerEventRecord = InnerEventRecord.builder()
                .eventId(1L)
                .channelId(1L)
                .userId(1L)
                .type(InnerEventType.DELETED_BOARD_IN_CHANNEL)
                .sentType(SentType.INIT)
                .build();
        given(innerEventOutBoxMapper.findInnerEventRecord(any(InnerEventRecord.class)))
                .willReturn(Optional.of(innerEventRecord));
        willDoNothing().given(deleteChannelBoardsUseCase).deleteChannelBoards(1L);
        willDoNothing().given(innerEventOutBoxMapper).updateInnerEventRecord(SentType.SEND_SUCCESS, 1L);

        boardEventHandler.boardsDeleteEventAfterHandler(new DeleteBoardsEvent(1L, 1L));

        then(innerEventOutBoxMapper).should(times(1)).findInnerEventRecord(any(InnerEventRecord.class));
        then(deleteChannelBoardsUseCase).should(times(1)).deleteChannelBoards(1L);
        then(innerEventOutBoxMapper).should(times(1)).updateInnerEventRecord(SentType.SEND_SUCCESS, 1L);
    }

    @Test
    @DisplayName("게시글 리스트 삭제 이벤트 예외 테스트")
    void delete_boards_event_after_exception() {
        InnerEventRecord innerEventRecord = InnerEventRecord.builder()
                .eventId(1L)
                .channelId(1L)
                .userId(1L)
                .type(InnerEventType.DELETED_BOARD_IN_CHANNEL)
                .sentType(SentType.INIT)
                .build();
        given(innerEventOutBoxMapper.findInnerEventRecord(any(InnerEventRecord.class)))
                .willReturn(Optional.of(innerEventRecord));
        willThrow(RuntimeException.class).given(deleteChannelBoardsUseCase).deleteChannelBoards(1L);
        willDoNothing().given(innerEventOutBoxMapper).updateInnerEventRecord(SentType.SEND_FAIL, 1L);

        boardEventHandler.boardsDeleteEventAfterHandler(new DeleteBoardsEvent(1L, 1L));

        then(innerEventOutBoxMapper).should(times(1)).findInnerEventRecord(any(InnerEventRecord.class));
        then(deleteChannelBoardsUseCase).should(times(1)).deleteChannelBoards(1L);
        then(innerEventOutBoxMapper).should(times(1)).updateInnerEventRecord(SentType.SEND_FAIL, 1L);
    }

    @Test
    @DisplayName("길드 내 게시글 리스트 삭제 이벤트 저장 테스트")
    void delete_boards_guild_event_before() {
        DeleteBoardsInGuildEvent deleteBoardsInGuildEvent = new DeleteBoardsInGuildEvent(
                List.of(ChannelId.make(1L), ChannelId.make(2L), ChannelId.make(3L)));
        willDoNothing().given(innerEventOutBoxMapper).insertInnerEventRecords(anyList());

        boardEventHandler.boardsDeleteInGuildEventBeforeHandler(deleteBoardsInGuildEvent);

        then(innerEventOutBoxMapper).should(times(1)).insertInnerEventRecords(anyList());
    }

    @Test
    @DisplayName("길드 내 게시판 리스트 삭제 이벤트 테스트")
    void delete_boards_guild_event_after() {
        DeleteBoardsInGuildEvent deleteBoardsInGuildEvent = new DeleteBoardsInGuildEvent(
                List.of(ChannelId.make(1L), ChannelId.make(2L), ChannelId.make(3L)));
        List<InnerEventRecord> records = deleteBoardsInGuildEvent.channelIds().stream()
                .map(ChannelId::getId)
                .map(channelId ->
                        InnerEventRecord.builder()
                                .eventId(channelId)
                                .type(InnerEventType.DELETED_BOARD_IN_CHANNELS)
                                .sentType(SentType.INIT)
                                .channelId(channelId)
                                .build()
                )
                .toList();
        given(innerEventOutBoxMapper.findInnerEventRecords(anyList())).willReturn(records);
        willDoNothing().given(deleteChannelsBoardsUseCase).deleteInChannels(deleteBoardsInGuildEvent.channelIds());
        willDoNothing().given(innerEventOutBoxMapper).updateInnerEventRecords(SentType.SEND_SUCCESS,records);

        boardEventHandler.boardsDeleteInGuildEventAfterHandler(deleteBoardsInGuildEvent);

        then(innerEventOutBoxMapper).should(times(1)).findInnerEventRecords(anyList());
        then(deleteChannelsBoardsUseCase).should(times(1)).deleteInChannels(deleteBoardsInGuildEvent.channelIds());
        then(innerEventOutBoxMapper).should(times(1)).updateInnerEventRecords(SentType.SEND_SUCCESS,records);
    }

    @Test
    @DisplayName("길드 내 게시판 리스트 삭제 이벤트 예외 테스트")
    void delete_boards_guild_event_exception() {
        DeleteBoardsInGuildEvent deleteBoardsInGuildEvent = new DeleteBoardsInGuildEvent(
                List.of(ChannelId.make(1L), ChannelId.make(2L), ChannelId.make(3L)));
        List<InnerEventRecord> records = deleteBoardsInGuildEvent.channelIds().stream()
                .map(ChannelId::getId)
                .map(channelId ->
                        InnerEventRecord.builder()
                                .eventId(channelId)
                                .type(InnerEventType.DELETED_BOARD_IN_CHANNELS)
                                .sentType(SentType.INIT)
                                .channelId(channelId)
                                .build()
                )
                .toList();
        given(innerEventOutBoxMapper.findInnerEventRecords(anyList())).willReturn(records);
        willThrow(RuntimeException.class).given(deleteChannelsBoardsUseCase).deleteInChannels(deleteBoardsInGuildEvent.channelIds());
        willDoNothing().given(innerEventOutBoxMapper).updateInnerEventRecords(SentType.SEND_FAIL,records);

        boardEventHandler.boardsDeleteInGuildEventAfterHandler(deleteBoardsInGuildEvent);

        then(innerEventOutBoxMapper).should(times(1)).findInnerEventRecords(anyList());
        then(deleteChannelsBoardsUseCase).should(times(1)).deleteInChannels(deleteBoardsInGuildEvent.channelIds());
        then(innerEventOutBoxMapper).should(times(1)).updateInnerEventRecords(SentType.SEND_FAIL,records);
    }

}