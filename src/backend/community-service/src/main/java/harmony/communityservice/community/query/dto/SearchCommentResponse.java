package harmony.communityservice.community.query.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchCommentResponse {

    private Long commentId;
    private Long boardId;
    private String writerName;
    private String comment;
    private Long userId;
    private boolean modified;
    private String writerProfile;
    private String createdAt;

    @Builder
    public SearchCommentResponse(Long commentId, Long boardId, String writerName, String comment, Long userId,
                                 boolean modified, String writerProfile, String createdAt) {
        this.commentId = commentId;
        this.boardId = boardId;
        this.writerName = writerName;
        this.comment = comment;
        this.userId = userId;
        this.modified = modified;
        this.writerProfile = writerProfile;
        this.createdAt = createdAt;
    }
}
