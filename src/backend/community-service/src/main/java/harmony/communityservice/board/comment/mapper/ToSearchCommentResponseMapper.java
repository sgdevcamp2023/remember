package harmony.communityservice.board.comment.mapper;

import harmony.communityservice.board.comment.dto.SearchCommentResponse;
import harmony.communityservice.board.comment.domain.Comment;
import java.time.ZoneId;

public class ToSearchCommentResponseMapper {

    public static SearchCommentResponse convert(Comment comment, long boardId, int commentId) {
        return SearchCommentResponse.builder()
                .commentId(commentId)
                .comment(comment.getComment())
                .writerName(comment.getWriterInfo().getCommonUserInfo().getNickname())
                .userId(comment.getWriterInfo().getWriterId())
                .writerProfile(comment.getWriterInfo().getCommonUserInfo().getProfile())
                .modified(comment.getType())
                .boardId(boardId)
                .createdAt(comment.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")).toEpochSecond())
                .build();
    }
}
