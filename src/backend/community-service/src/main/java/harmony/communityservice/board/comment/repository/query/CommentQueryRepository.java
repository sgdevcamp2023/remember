package harmony.communityservice.board.comment.repository.query;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import harmony.communityservice.board.comment.domain.Comment;
import java.util.List;

public interface CommentQueryRepository {

    Long countListByBoardId(BoardIdJpaVO boardId);

    List<Comment> findListByBoardId(BoardIdJpaVO boardId);
}
