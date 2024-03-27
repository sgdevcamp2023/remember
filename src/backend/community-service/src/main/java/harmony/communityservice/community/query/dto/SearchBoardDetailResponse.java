package harmony.communityservice.community.query.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record SearchBoardDetailResponse(
        Long boardId,
        String title,
        String content,
        String writerName,
        Long userId,
        boolean modified,
        String createdAt,
        SearchCommentsResponse commentsResponseDto,
        SearchImagesResponse imagesResponseDto,
        SearchEmojisResponse emojisResponseDto
) {
}

