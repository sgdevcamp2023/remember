package harmony.communityservice.board.board.application.port.in;

import harmony.communityservice.board.board.domain.ModifiedType;
import harmony.communityservice.board.emoji.application.port.in.LoadEmojisResponse;
import lombok.Builder;

@Builder(toBuilder = true)
public record LoadBoardResponse(
        Long boardId,
        Long channelId,
        String title,
        String content,
        String writer,
        Long userId,
        ModifiedType modified,
        Long createdAt,
        LoadEmojisResponse searchEmojiResponses,
        Long commentCount
) {
}
