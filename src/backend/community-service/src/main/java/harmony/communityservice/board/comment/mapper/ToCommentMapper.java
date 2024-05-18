package harmony.communityservice.board.comment.mapper;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import harmony.communityservice.board.comment.dto.RegisterCommentRequest;
import harmony.communityservice.board.comment.domain.Comment;

public class ToCommentMapper {

    public static Comment convert(RegisterCommentRequest registerCommentRequest) {
        return Comment.builder()
                .comment(registerCommentRequest.comment())
                .writerId(registerCommentRequest.userId())
                .boardId(BoardIdJpaVO.make(registerCommentRequest.boardId()))
                .writerName(registerCommentRequest.writerName())
                .writerProfile(registerCommentRequest.writerProfile())
                .build();
    }
}
