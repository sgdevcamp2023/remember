package harmony.communityservice.board.comment.mapper;

import harmony.communityservice.board.comment.dto.RegisterCommentRequest;
import harmony.communityservice.board.domain.Comment;

public class ToCommentMapper {

    public static Comment convert(RegisterCommentRequest registerCommentRequest) {
        return Comment.builder()
                .comment(registerCommentRequest.comment())
                .writerId(registerCommentRequest.userId())
                .writerName(registerCommentRequest.writerName())
                .writerProfile(registerCommentRequest.writerProfile())
                .build();
    }
}
