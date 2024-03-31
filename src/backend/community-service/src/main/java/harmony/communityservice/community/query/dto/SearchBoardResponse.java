package harmony.communityservice.community.query.dto;

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
        boolean modified,
        String createdAt,
        List<SearchEmojiResponse> searchEmojiResponses,
        int commentCount
) {
}
