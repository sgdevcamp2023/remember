package harmony.communityservice.board.comment.application.service;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.comment.application.port.in.RegisterCommentCommand;
import harmony.communityservice.board.comment.domain.Comment;
import harmony.communityservice.board.comment.domain.Comment.CommentId;
import harmony.communityservice.domain.Threshold;

class CommentMapper {

    static Comment convert(RegisterCommentCommand registerCommentCommand) {
        return Comment.builder()
                .commentId(CommentId.make(Threshold.MIN.getValue()))
                .comment(registerCommentCommand.comment())
                .profile(registerCommentCommand.writerProfile())
                .boardId(BoardId.make(registerCommentCommand.boardId()))
                .nickname(registerCommentCommand.writerName())
                .writerId(registerCommentCommand.userId())
                .build();
    }
}
