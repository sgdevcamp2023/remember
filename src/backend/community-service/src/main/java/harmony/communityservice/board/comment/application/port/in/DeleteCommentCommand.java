package harmony.communityservice.board.comment.application.port.in;

public record DeleteCommentCommand(Long commentId, Long userId, Long boardId) {
}
