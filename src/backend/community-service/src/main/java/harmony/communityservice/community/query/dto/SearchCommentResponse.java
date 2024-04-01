package harmony.communityservice.community.query.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record SearchCommentResponse(
        Long commentId,
        Long boardId,
        String writerName,
        String comment,
        Long userId,
        boolean modified,
        String writerProfile,
        String createdAt
) {
}

