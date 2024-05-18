package harmony.communityservice.board.comment.repository.command;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import harmony.communityservice.board.comment.domain.Comment;
import harmony.communityservice.board.comment.domain.CommentId;
import java.util.List;
import java.util.Optional;

public interface CommentCommandRepository {

    void save(Comment comment);

    void deleteById(CommentId commentId);

    Optional<Comment> findById(CommentId commentId);

    void deleteListByBoardId(BoardIdJpaVO boardId);

    void deleteListByBoardIds(List<BoardIdJpaVO> boardIds);
}
