package harmony.communityservice.board.comment.application.port.in;

import lombok.Builder;

@Builder(toBuilder = true)
public record ModifyCommentCommand(Long userId,
                                   Long boardId,
                                   Long commentId,
                                   String comment) {
}
