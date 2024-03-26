package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Comment;
import harmony.communityservice.community.query.dto.SearchCommentResponse;

public class ToSearchCommentResponseMapper {

    public static SearchCommentResponse convert(Comment comment, long boardId) {
        return SearchCommentResponse.builder()
                .commentId(comment.getCommentId())
                .comment(comment.getComment())
                .writerName(comment.getWriterName())
                .userId(comment.getUserId())
                .writerProfile(comment.getWriterProfile())
                .modified(comment.isModified())
                .boardId(boardId)
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
