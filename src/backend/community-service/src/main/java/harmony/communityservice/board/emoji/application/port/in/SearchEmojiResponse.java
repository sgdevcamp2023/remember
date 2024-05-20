package harmony.communityservice.board.emoji.application.port.in;

import java.util.List;
import lombok.Builder;

@Builder(toBuilder = true)
public record SearchEmojiResponse(
        Long emojiId,
        Long boardId,
        Long commentId,
        Long emojiType,
        List<Long> emojiUsers
) {
}
