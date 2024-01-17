package harmony.communityservice.community.query.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private Long commentId;
    private Long boardId;
    private String writerName;
    private Long userId;
    private boolean modified;
    private String writerProfile;
    private LocalDateTime createdAt;

    @Builder
    public CommentResponseDto(Long commentId, Long boardId, String writerName, Long userId, boolean modified,
                              String writerProfile, LocalDateTime createdAt) {
        this.commentId = commentId;
        this.boardId = boardId;
        this.writerName = writerName;
        this.userId = userId;
        this.modified = modified;
        this.writerProfile = writerProfile;
        this.createdAt = createdAt;
    }
}
