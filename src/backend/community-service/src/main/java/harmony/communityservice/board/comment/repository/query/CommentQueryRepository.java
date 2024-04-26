package harmony.communityservice.board.comment.repository.query;

import harmony.communityservice.board.board.domain.BoardId;
import harmony.communityservice.board.comment.domain.Comment;
import java.util.List;

public interface CommentQueryRepository {

    Long countListByBoardId(BoardId boardId);

    List<Comment> findListByBoardId(BoardId boardId);
}
