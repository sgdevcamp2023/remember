package harmony.communityservice.board.comment.adapter.out.persistence;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.comment.domain.Comment;
import harmony.communityservice.board.comment.domain.Comment.CommentId;
import harmony.communityservice.board.comment.domain.ModifiedType;

class CommentMapper {

    static Comment convert(CommentEntity commentEntity) {
        return Comment.builder()
                .nickname(commentEntity.getWriterInfo().getCommonUserInfo().getNickname())
                .writerId(commentEntity.getWriterInfo().getWriterId())
                .profile(commentEntity.getWriterInfo().getCommonUserInfo().getProfile())
                .comment(commentEntity.getComment())
                .commentId(CommentId.make(commentEntity.getCommentId().getId()))
                .boardId(BoardId.make(commentEntity.getBoardId().getId()))
                .createdAt(commentEntity.getCreatedAt())
                .type(ModifiedType.valueOf(commentEntity.getType().name()))
                .build();
    }
}
