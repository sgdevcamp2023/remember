package harmony.communityservice.board.board.dto;

import harmony.communityservice.board.comment.dto.SearchCommentsResponse;
import harmony.communityservice.board.emoji.dto.SearchEmojisResponse;
import harmony.communityservice.common.domain.ModifiedType;
import lombok.Builder;

@Builder(toBuilder = true)
public record SearchBoardDetailResponse(
        Long boardId,
        String title,
        String content,
        String writerName,
        Long userId,
        ModifiedType modified,
        Long createdAt,
        SearchCommentsResponse searchCommentsResponse,
        SearchImagesResponse searchImagesResponse,
        SearchEmojisResponse searchEmojisResponse
) {
}

