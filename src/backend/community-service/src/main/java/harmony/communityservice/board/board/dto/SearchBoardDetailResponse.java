package harmony.communityservice.board.board.dto;

import harmony.communityservice.board.comment.application.port.in.LoadCommentsResponse;
import harmony.communityservice.board.emoji.application.port.in.SearchEmojisResponse;
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
        LoadCommentsResponse searchCommentsResponse,
        SearchImagesResponse searchImagesResponse,
        SearchEmojisResponse searchEmojisResponse
) {
}

