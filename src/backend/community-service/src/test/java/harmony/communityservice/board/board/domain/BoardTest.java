package harmony.communityservice.board.board.domain;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.exception.WrongThresholdRangeException;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class BoardTest {

    @Test
    @DisplayName("같은 식별자면 같은 객체로 인식 테스트")
    void same_board() {
        Board firstBoard = Board.builder()
                .type(ModifiedType.NOT_YET)
                .profile("http://cdn.com/test")
                .boardId(BoardId.make(1L))
                .channelId(ChannelId.make(1L))
                .writerId(1L)
                .username("test")
                .title("title")
                .content("test")
                .build();

        Board secondBoard = Board.builder()
                .type(ModifiedType.NOT_YET)
                .profile("http://cdn.com/test")
                .boardId(BoardId.make(1L))
                .channelId(ChannelId.make(1L))
                .writerId(1L)
                .username("test")
                .title("title")
                .content("test")
                .build();

        boolean equals = firstBoard.equals(secondBoard);

        assertSame(true, equals);
    }

    @Test
    @DisplayName("다른 식별자면 다른 객체로 인식 테스트")
    void different_board() {
        Board firstBoard = Board.builder()
                .type(ModifiedType.NOT_YET)
                .profile("http://cdn.com/test")
                .boardId(BoardId.make(1L))
                .channelId(ChannelId.make(1L))
                .writerId(1L)
                .username("test")
                .title("title")
                .content("test")
                .build();

        Board secondBoard = Board.builder()
                .type(ModifiedType.NOT_YET)
                .profile("http://cdn.com/test")
                .boardId(BoardId.make(2L))
                .channelId(ChannelId.make(1L))
                .writerId(1L)
                .username("test")
                .title("title")
                .content("test")
                .build();

        boolean equals = firstBoard.equals(secondBoard);

        assertSame(false, equals);
    }

    @Test
    @DisplayName("profile이 없을 때 예외 처리 테스트")
    void not_exists_profile() {
        assertThrows(NotFoundDataException.class, () -> Board.builder()
                .type(ModifiedType.NOT_YET)
                .boardId(BoardId.make(1L))
                .channelId(ChannelId.make(1L))
                .writerId(1L)
                .username("test")
                .title("title")
                .content("test")
                .build());
    }

    @Test
    @DisplayName("name이 없을 때 예외 처리 테스트")
    void not_exists_nickname() {
        assertThrows(NotFoundDataException.class, () -> Board.builder()
                .type(ModifiedType.NOT_YET)
                .profile("http://cdn.com/test")
                .boardId(BoardId.make(1L))
                .channelId(ChannelId.make(1L))
                .writerId(1L)
                .title("title")
                .content("test")
                .build());
    }

    @Test
    @DisplayName("content가 없을 때 예외 처리 테스트")
    void not_exists_content() {
        assertThrows(NotFoundDataException.class, () -> Board.builder()
                .type(ModifiedType.NOT_YET)
                .profile("http://cdn.com/test")
                .boardId(BoardId.make(1L))
                .channelId(ChannelId.make(1L))
                .writerId(1L)
                .title("title")
                .username("test")
                .build());
    }

    @Test
    @DisplayName("writerId가 없을 때 예외 처리 테스트")
    void not_exists_writerId() {
        assertThrows(NotFoundDataException.class, () -> Board.builder()
                .type(ModifiedType.NOT_YET)
                .profile("http://cdn.com/test")
                .boardId(BoardId.make(1L))
                .channelId(ChannelId.make(1L))
                .username("test")
                .title("title")
                .content("test")
                .build());
    }

    @Test
    @DisplayName("channelId가 없을 때 예외 처리 테스트")
    void not_exists_channelId() {
        assertThrows(NotFoundDataException.class, () -> Board.builder()
                .type(ModifiedType.NOT_YET)
                .profile("http://cdn.com/test")
                .boardId(BoardId.make(1L))
                .writerId(1L)
                .username("test")
                .title("title")
                .content("test")
                .build());
    }

    @Test
    @DisplayName("title가 없을 때 예외 처리 테스트")
    void not_exists_title() {
        assertThrows(NotFoundDataException.class, () -> Board.builder()
                .type(ModifiedType.NOT_YET)
                .profile("http://cdn.com/test")
                .boardId(BoardId.make(1L))
                .writerId(1L)
                .username("test")
                .channelId(ChannelId.make(1L))
                .content("test")
                .build());
    }

    @ParameterizedTest
    @DisplayName("writerId 범위 테스트")
    @ValueSource(longs = {-1L, 0L, -100L, -10000L, -1000L})
    void writer_id_range_threshold(long writerId) {
        assertThrows(WrongThresholdRangeException.class, () -> Board.builder()
                .type(ModifiedType.NOT_YET)
                .profile("http://cdn.com/test")
                .boardId(BoardId.make(1L))
                .channelId(ChannelId.make(1L))
                .writerId(writerId)
                .username("test")
                .title("title")
                .content("test")
                .build());
    }

    @ParameterizedTest
    @DisplayName("channelId 범위 테스트")
    @ValueSource(longs = {-1L, 0L, -100L, -10000L, -1000L})
    void channel_id_range_threshold(long channelId) {
        assertThrows(WrongThresholdRangeException.class, () -> Board.builder()
                .type(ModifiedType.NOT_YET)
                .profile("http://cdn.com/test")
                .boardId(BoardId.make(1L))
                .channelId(ChannelId.make(channelId))
                .writerId(1L)
                .username("test")
                .content("test")
                .build());
    }

    @ParameterizedTest
    @DisplayName("boardId 범위 테스트")
    @ValueSource(longs = {-1L, 0L, -100L, -10000L, -1000L})
    void board_id_range_threshold(long boardId) {

        assertThrows(WrongThresholdRangeException.class, () -> Board.builder()
                .type(ModifiedType.NOT_YET)
                .profile("http://cdn.com/test")
                .boardId(BoardId.make(boardId))
                .channelId(ChannelId.make(1L))
                .writerId(1L)
                .username("test")
                .content("test")
                .build());
    }

}