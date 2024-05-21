package harmony.communityservice.board.comment.adapter.out.persistence;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.comment.application.port.out.DeleteCommentPort;
import harmony.communityservice.board.comment.application.port.out.DeleteCommentsPort;
import harmony.communityservice.board.comment.application.port.out.ModifyCommentPort;
import harmony.communityservice.board.comment.application.port.out.RegisterCommentPort;
import harmony.communityservice.board.comment.domain.Comment;
import harmony.communityservice.board.comment.domain.Comment.CommentId;
import harmony.communityservice.common.annotation.PersistenceAdapter;
import harmony.communityservice.common.domain.ModifiedType;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class CommentCommandPersistenceAdapter implements RegisterCommentPort, ModifyCommentPort, DeleteCommentPort,
        DeleteCommentsPort {

    private final CommentCommandRepository commentCommandRepository;

    @Override
    public void register(Comment comment) {
        CommentEntity commentEntity = CommentEntityMapper.convert(comment);
        commentCommandRepository.save(commentEntity);
    }

    @Override
    public void modify(CommentId commentId, String comment, UserId userId) {
        commentCommandRepository.updateComment(comment, ModifiedType.MODIFY, CommentIdJpaVO.make(commentId.getId()),
                userId.getId());
    }

    @Override
    public void delete(CommentId commentId) {
        commentCommandRepository.deleteById(CommentIdJpaVO.make(commentId.getId()));
    }

    @Override
    public void deleteByBoardId(BoardId boardId) {
        commentCommandRepository.deleteCommentsByBoardId(BoardIdJpaVO.make(boardId.getId()));
    }

    @Override
    public void deleteByBoardIds(List<BoardId> boardIds) {
        List<BoardIdJpaVO> boardIdJpaVOS = boardIds.stream()
                .map(boardId -> BoardIdJpaVO.make(boardId.getId()))
                .toList();
        commentCommandRepository.deleteAllByBoardIds(boardIdJpaVOS);
    }
}
