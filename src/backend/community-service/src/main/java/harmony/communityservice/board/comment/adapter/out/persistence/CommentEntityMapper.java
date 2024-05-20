package harmony.communityservice.board.comment.adapter.out.persistence;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import harmony.communityservice.board.comment.domain.Comment;

class CommentEntityMapper {

    static CommentEntity convert(Comment comment) {
        return CommentEntity.builder()
                .boardId(BoardIdJpaVO.make(comment.getBoardId().getId()))
                .writerName(comment.getWriterInfo().getCommonUserInfo().getNickname())
                .writerProfile(comment.getWriterInfo().getCommonUserInfo().getProfile())
                .comment(comment.getComment())
                .writerId(comment.getWriterInfo().getWriterId())
                .build();
    }
}
