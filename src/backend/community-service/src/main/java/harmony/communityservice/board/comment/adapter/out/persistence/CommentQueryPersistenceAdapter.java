package harmony.communityservice.board.comment.adapter.out.persistence;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.comment.application.port.out.CountCommentPort;
import harmony.communityservice.board.comment.application.port.out.LoadCommentsPort;
import harmony.communityservice.board.comment.domain.Comment;
import harmony.communityservice.common.annotation.PersistenceAdapter;
import java.util.List;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class CommentQueryPersistenceAdapter implements CountCommentPort, LoadCommentsPort {

    private final CommentQueryRepository commentQueryRepository;


    @Override
    public Long count(BoardId boardId) {
        return commentQueryRepository.countCommentsByBoardId(BoardIdJpaVO.make(boardId.getId()));
    }

    @Override
    public List<Comment> loadByBoardId(BoardId boardId) {
        List<CommentEntity> commentEntities = commentQueryRepository.findCommentsByBoardId(
                BoardIdJpaVO.make(boardId.getId()));
        return commentEntities.stream()
                .map(CommentMapper::convert)
                .toList();
    }
}
