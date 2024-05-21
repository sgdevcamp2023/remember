package harmony.communityservice.board.comment.application.port.in;

import lombok.Builder;

@Builder(toBuilder = true)
public record RegisterCommentCommand(Long userId,
                                     Long boardId,
                                     String comment,
                                     String writerName,
                                     String writerProfile) {
}
