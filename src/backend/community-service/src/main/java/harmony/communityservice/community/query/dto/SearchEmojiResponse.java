package harmony.communityservice.community.query.dto;

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
