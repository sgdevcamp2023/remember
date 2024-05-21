package harmony.communityservice.board.board.application.port.in;

import harmony.communityservice.board.board.domain.ModifiedType;
import harmony.communityservice.board.comment.application.port.in.LoadCommentsResponse;
import harmony.communityservice.board.emoji.application.port.in.LoadEmojisResponse;
import lombok.Builder;

@Builder(toBuilder = true)
public record LoadBoardDetailResponse(
        Long boardId,
        String title,
        String content,
        String writerName,
        Long userId,
        ModifiedType modified,
        Long createdAt,
        LoadCommentsResponse loadCommentsResponse,
        LoadImagesResponse loadImagesResponse,
        LoadEmojisResponse loadEmojisResponse
) {
}

