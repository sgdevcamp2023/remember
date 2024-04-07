package harmony.communityservice.community.query.dto;

import harmony.communityservice.community.domain.ModifiedType;
import lombok.Builder;

@Builder(toBuilder = true)
public record SearchCommentResponse(
        Long commentId,
        Long boardId,
        String writerName,
        String comment,
        Long userId,
        ModifiedType modified,
        String writerProfile,
        String createdAt
) {
}

