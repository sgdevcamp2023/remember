package harmony.communityservice.community.query.dto;

import harmony.communityservice.community.domain.ModifiedType;
import java.util.List;
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
        String createdAt,
        List<SearchEmojiResponse> searchEmojiResponses,
        Long commentCount
) {
}
