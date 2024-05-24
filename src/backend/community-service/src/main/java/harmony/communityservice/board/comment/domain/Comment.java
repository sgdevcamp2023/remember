package harmony.communityservice.board.comment.domain;

import static harmony.communityservice.domain.Threshold.MIN;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.comment.domain.Comment.CommentId;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.exception.WrongThresholdRangeException;
import harmony.communityservice.domain.Domain;
import harmony.communityservice.domain.ValueObject;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Comment extends Domain<Comment, CommentId> {

    private CommentId commentId;

    private String comment;

    private BoardId boardId;

    private WriterInfo writerInfo;
    private Instant createdAt;
    private ModifiedType type;

    @Builder
    public Comment(BoardId boardId, String comment, CommentId commentId, Long writerId, String nickname,
                   String profile, Instant createdAt, ModifiedType type) {
        verifyBoardId(boardId);
        this.boardId = boardId;
        verifyComment(comment, commentId);
        this.comment = comment;
        this.commentId = commentId;
        this.writerInfo = makeWriterInfo(writerId, nickname, profile);
        this.createdAt = createdAt;
        this.type = type;
    }

    private WriterInfo makeWriterInfo(Long writerId, String nickname, String profile) {
        return WriterInfo.builder()
                .userName(nickname)
                .profile(profile)
                .writerId(writerId)
                .build();
    }

    private void verifyBoardId(BoardId boardId) {
        if (boardId == null) {
            throw new NotFoundDataException("boardId를 찾을 수 없습니다");
        }

        if (boardId.getId() < MIN.getValue()) {
            throw new WrongThresholdRangeException("boardId의 범위가 1미만 입니다.");
        }
    }

    private void verifyComment(String comment, CommentId commentId) {
        if (comment == null) {
            throw new NotFoundDataException("comment를 찾을 수 없습니다");
        }

        if (commentId != null && commentId.getId() < MIN.getValue()) {
            throw new WrongThresholdRangeException("commentId의 범위가 1미만 입니다.");
        }
    }

    @Override
    public CommentId getId() {
        return commentId;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CommentId extends ValueObject<CommentId> {
        private final Long id;

        public static CommentId make(Long commentId) {
            return new CommentId(commentId);
        }

        @Override
        protected Object[] getEqualityFields() {
            return new Object[]{id};
        }
    }
}
