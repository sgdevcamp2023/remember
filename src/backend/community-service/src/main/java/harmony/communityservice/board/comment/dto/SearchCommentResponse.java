package harmony.communityservice.board.comment.dto;

import harmony.communityservice.generic.ModifiedType;
import lombok.Builder;

@Builder(toBuilder = true)
public record SearchCommentResponse(
        int commentId,
        Long boardId,
        String writerName,
        String comment,
        Long userId,
        ModifiedType modified,
        String writerProfile,
        String createdAt
) {
}

