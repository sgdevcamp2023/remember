package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Comment;
import harmony.communityservice.community.query.dto.CommentResponseDto;

public class ToCommentResponseDtoMapper {

    public static CommentResponseDto convert(Comment comment, long boardId) {
        return CommentResponseDto.builder()
                .commentId(comment.getCommentId())
                .writerName(comment.getWriterName())
                .userId(comment.getUserId())
                .writerProfile(comment.getWriterProfile())
                .modified(comment.isModified())
                .boardId(boardId)
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
