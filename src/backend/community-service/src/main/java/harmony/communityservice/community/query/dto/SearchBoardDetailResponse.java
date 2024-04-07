package harmony.communityservice.community.query.dto;

import harmony.communityservice.community.domain.ModifiedType;
import lombok.Builder;

@Builder(toBuilder = true)
public record SearchBoardDetailResponse(
        Long boardId,
        String title,
        String content,
        String writerName,
        Long userId,
        ModifiedType modified,
        String createdAt,
        SearchCommentsResponse searchCommentsResponse,
        SearchImagesResponse searchImagesResponse,
        SearchEmojisResponse searchEmojisResponse
) {
}

