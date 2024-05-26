package harmony.communityservice.board.comment.domain;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.comment.domain.Comment.CommentId;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.exception.WrongThresholdRangeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CommentTest {

    @Test
    @DisplayName("같은 식별자면 같은 객체로 인식 테스트")
    void same_comment() {

        Comment firstComment = Comment.builder()
                .type(ModifiedType.NOT_YET)
                .profile("http://cdn.com/test")
                .commentId(CommentId.make(1L))
                .boardId(BoardId.make(1L))
                .writerId(1L)
                .nickname("test")
                .comment("test")
                .build();

        Comment secondComment = Comment.builder()
                .type(ModifiedType.NOT_YET)
                .profile("http://cdn.com/test")
                .commentId(CommentId.make(1L))
                .boardId(BoardId.make(1L))
                .writerId(1L)
                .nickname("test")
                .comment("test")
                .build();

        boolean equals = firstComment.equals(secondComment);

        assertSame(true, equals);
    }

    @Test
    @DisplayName("다른 식별자면 다른 객체로 인식 테스트")
    void different_comment() {

        Comment firstComment = Comment.builder()
                .type(ModifiedType.NOT_YET)
                .profile("http://cdn.com/test")
                .commentId(CommentId.make(1L))
                .boardId(BoardId.make(1L))
                .writerId(1L)
                .nickname("test")
                .comment("test")
                .build();

        Comment secondComment = Comment.builder()
                .type(ModifiedType.NOT_YET)
                .profile("http://cdn.com/test")
                .commentId(CommentId.make(2L))
                .boardId(BoardId.make(1L))
                .writerId(1L)
                .nickname("test")
                .comment("test")
                .build();

        boolean equals = firstComment.equals(secondComment);

        assertSame(false, equals);
    }

    @Test
    @DisplayName("profile이 없을 때 예외 처리 테스트")
    void not_exists_profile() {
        assertThrows(NotFoundDataException.class, () -> Comment.builder()
                .type(ModifiedType.NOT_YET)
                .commentId(CommentId.make(1L))
                .boardId(BoardId.make(1L))
                .writerId(1L)
                .nickname("test")
                .comment("test")
                .build());
    }

    @Test
    @DisplayName("name이 없을 때 예외 처리 테스트")
    void not_exists_nickname() {
        assertThrows(NotFoundDataException.class, () -> Comment.builder()
                .type(ModifiedType.NOT_YET)
                .profile("http://cdn.com/test")
                .commentId(CommentId.make(1L))
                .boardId(BoardId.make(1L))
                .writerId(1L)
                .comment("test")
                .build());
    }

    @Test
    @DisplayName("comment가 없을 때 예외 처리 테스트")
    void not_exists_comment() {
        assertThrows(NotFoundDataException.class, () -> Comment.builder()
                .type(ModifiedType.NOT_YET)
                .profile("http://cdn.com/test")
                .commentId(CommentId.make(1L))
                .boardId(BoardId.make(1L))
                .writerId(1L)
                .nickname("test")
                .build());
    }

    @Test
    @DisplayName("writerId가 없을 때 예외 처리 테스트")
    void not_exists_writerId() {
        assertThrows(NotFoundDataException.class, () -> Comment.builder()
                .type(ModifiedType.NOT_YET)
                .profile("http://cdn.com/test")
                .commentId(CommentId.make(1L))
                .boardId(BoardId.make(1L))
                .nickname("test")
                .comment("test")
                .build());
    }

    @Test
    @DisplayName("boardId가 없을 때 예외 처리 테스트")
    void not_exists_boardId() {
        assertThrows(NotFoundDataException.class, () -> Comment.builder()
                .type(ModifiedType.NOT_YET)
                .profile("http://cdn.com/test")
                .commentId(CommentId.make(1L))
                .writerId(1L)
                .nickname("test")
                .comment("test")
                .build());
    }

    @ParameterizedTest
    @DisplayName("writerId 범위 테스트")
    @ValueSource(longs = {-1L, 0L, -100L, -10000L, -1000L})
    void writer_id_range_threshold(long writerId) {

        assertThrows(WrongThresholdRangeException.class, () -> Comment.builder()
                .type(ModifiedType.NOT_YET)
                .profile("http://cdn.com/test")
                .commentId(CommentId.make(1L))
                .boardId(BoardId.make(1L))
                .writerId(writerId)
                .nickname("test")
                .comment("test")
                .build());
    }

    @ParameterizedTest
    @DisplayName("commentId 범위 테스트")
    @ValueSource(longs = {-1L, 0L, -100L, -10000L, -1000L})
    void comment_id_range_threshold(long commentId) {

        assertThrows(WrongThresholdRangeException.class, () -> Comment.builder()
                .type(ModifiedType.NOT_YET)
                .profile("http://cdn.com/test")
                .commentId(CommentId.make(commentId))
                .boardId(BoardId.make(1L))
                .writerId(1L)
                .nickname("test")
                .comment("test")
                .build());
    }

    @ParameterizedTest
    @DisplayName("boardId 범위 테스트")
    @ValueSource(longs = {-1L, 0L, -100L, -10000L, -1000L})
    void board_id_range_threshold(long boardId) {

        assertThrows(WrongThresholdRangeException.class, () -> Comment.builder()
                .type(ModifiedType.NOT_YET)
                .profile("http://cdn.com/test")
                .commentId(CommentId.make(1L))
                .boardId(BoardId.make(boardId))
                .writerId(1L)
                .nickname("test")
                .comment("test")
                .build());
    }
}