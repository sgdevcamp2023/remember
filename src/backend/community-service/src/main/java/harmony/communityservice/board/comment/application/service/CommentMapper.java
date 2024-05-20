package harmony.communityservice.board.comment.application.service;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.comment.application.port.in.RegisterCommentCommand;
import harmony.communityservice.board.comment.domain.Comment;

class CommentMapper {

    static Comment convert(RegisterCommentCommand registerCommentCommand) {
        return Comment.builder()
                .comment(registerCommentCommand.comment())
                .profile(registerCommentCommand.writerProfile())
                .boardId(BoardId.make(registerCommentCommand.boardId()))
                .nickname(registerCommentCommand.writerName())
                .writerId(registerCommentCommand.userId())
                .build();
    }
}
