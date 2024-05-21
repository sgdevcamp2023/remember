package harmony.communityservice.board.comment.application.service;

import harmony.communityservice.board.comment.application.port.in.LordCommentResponse;
import harmony.communityservice.board.comment.domain.Comment;
import java.time.ZoneId;

class LoadCommentResponseMapper {

    static LordCommentResponse convert(Comment comment) {
        return LordCommentResponse.builder()
                .commentId(comment.getCommentId().getId())
                .comment(comment.getComment())
                .writerName(comment.getWriterInfo().getCommonUserInfo().getNickname())
                .userId(comment.getWriterInfo().getWriterId())
                .writerProfile(comment.getWriterInfo().getCommonUserInfo().getProfile())
                .modified(comment.getType())
                .boardId(comment.getBoardId().getId())
                .createdAt(comment.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")).toEpochSecond())
                .build();
    }
}
