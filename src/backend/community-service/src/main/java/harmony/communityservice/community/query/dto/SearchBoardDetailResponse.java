package harmony.communityservice.community.query.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchBoardDetailResponse {
    private Long boardId;
    private String title;
    private String content;
    private String writerName;
    private Long userId;
    private boolean modified;
    private String createdAt;
    private SearchCommentsResponse commentsResponseDto;
    private SearchImagesResponse imagesResponseDto;
    private SearchEmojisResponse emojisResponseDto;

    @Builder
    public SearchBoardDetailResponse(Long boardId, String title, String content, String writerName, Long userId,
                                     boolean modified,
                                     String createdAt, SearchCommentsResponse commentsResponseDto,
                                     SearchImagesResponse imagesResponseDto, SearchEmojisResponse emojisResponseDto) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.writerName = writerName;
        this.userId = userId;
        this.modified = modified;
        this.createdAt = createdAt;
        this.commentsResponseDto = commentsResponseDto;
        this.imagesResponseDto = imagesResponseDto;
        this.emojisResponseDto = emojisResponseDto;
    }
}
