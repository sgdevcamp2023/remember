package harmony.communityservice.board.emoji.application.port.in;

import java.util.List;
import lombok.Builder;

@Builder(toBuilder = true)
public record LoadEmojiResponse(
        Long emojiId,
        Long boardId,
        Long commentId,
        Long emojiType,
        List<Long> emojiUsers
) {
}
