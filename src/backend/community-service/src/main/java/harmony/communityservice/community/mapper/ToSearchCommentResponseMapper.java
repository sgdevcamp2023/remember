package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Comment;
import harmony.communityservice.community.query.dto.SearchCommentResponse;

public class ToSearchCommentResponseMapper {

    public static SearchCommentResponse convert(Comment comment, long boardId) {
        return SearchCommentResponse.builder()
                .commentId(comment.getCommentId())
                .comment(comment.getComment())
                .writerName(comment.getWriterInfo().getCommonUserInfo().getNickname())
                .userId(comment.getWriterInfo().getWriterId())
                .writerProfile(comment.getWriterInfo().getCommonUserInfo().getProfile())
                .modified(comment.getModifiedInfo().getModifiedType())
                .boardId(boardId)
                .createdAt(comment.getCreationTime().getCreatedAt())
                .build();
    }
}
