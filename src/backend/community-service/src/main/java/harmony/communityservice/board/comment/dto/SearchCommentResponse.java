package harmony.communityservice.board.comment.dto;

import harmony.communityservice.common.domain.ModifiedType;
import java.time.LocalDateTime;
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
        Long createdAt
) {
}

