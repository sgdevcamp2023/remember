package harmony.communityservice.community.query.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchBoardResponse {

    private Long boardId;
    private Long channelId;
    private String title;
    private String content;
    private String writer;
    private Long userId;
    private boolean modified;
    private String createdAt;
    private List<SearchEmojiResponse> emojiResponseDtos = new ArrayList<>();
    private int CommentCount;

    @Builder
    public SearchBoardResponse(Long boardId, Long channelId, String title, String content, String writer, Long userId,
                               boolean modified, String createdAt, List<SearchEmojiResponse> emojiResponseDtos,
                               int commentCount) {
        this.boardId = boardId;
        this.channelId = channelId;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.userId = userId;
        this.modified = modified;
        this.createdAt = createdAt;
        this.emojiResponseDtos = emojiResponseDtos;
        CommentCount = commentCount;
    }
}
