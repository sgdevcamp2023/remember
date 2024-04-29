package harmony.communityservice.board.board.dto;

import harmony.communityservice.board.emoji.dto.SearchEmojisResponse;
import harmony.communityservice.common.domain.ModifiedType;
import lombok.Builder;

@Builder(toBuilder = true)
public record SearchBoardResponse(
        Long boardId,
        Long channelId,
        String title,
        String content,
        String writer,
        Long userId,
        ModifiedType modified,
        Long createdAt,
        SearchEmojisResponse searchEmojiResponses,
        Long commentCount
) {
}
