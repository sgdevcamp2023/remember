package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.RegisterCommentRequest;
import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.domain.Comment;

public class ToCommentMapper {

    public static Comment convert(RegisterCommentRequest registerCommentRequest, Board findBoard) {
        return Comment.builder()
                .comment(registerCommentRequest.comment())
                .board(findBoard)
                .writerId(registerCommentRequest.userId())
                .writerName(registerCommentRequest.writerName())
                .writerProfile(registerCommentRequest.writerProfile())
                .build();
    }
}
