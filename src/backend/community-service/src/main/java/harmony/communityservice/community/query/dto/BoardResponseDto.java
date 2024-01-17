package harmony.communityservice.community.query.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardResponseDto {
    private Long boardId;
    private String title;
    private String content;
    private String writerName;
    private Long userId;
    private boolean modified;
    private LocalDateTime createdAt;
    private CommentsResponseDto commentsResponseDto;
    private ImagesResponseDto imagesResponseDto;
    private EmojisResponseDto emojisResponseDto;

    @Builder
    public BoardResponseDto(Long boardId, String title, String content, String writerName, Long userId,
                            boolean modified,
                            LocalDateTime createdAt, CommentsResponseDto commentsResponseDto,
                            ImagesResponseDto imagesResponseDto, EmojisResponseDto emojisResponseDto) {
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
