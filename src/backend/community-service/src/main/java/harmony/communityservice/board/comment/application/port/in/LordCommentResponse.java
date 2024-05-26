package harmony.communityservice.board.comment.application.port.in;

import harmony.communityservice.board.comment.domain.ModifiedType;
import lombok.Builder;

@Builder(toBuilder = true)
public record LordCommentResponse(
        Long commentId,
        Long boardId,
        String writerName,
        String comment,
        Long userId,
        ModifiedType modified,
        String writerProfile,
        Long createdAt
) {
}