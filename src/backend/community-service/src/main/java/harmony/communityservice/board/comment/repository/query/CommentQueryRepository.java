package harmony.communityservice.board.comment.repository.query;

import harmony.communityservice.board.domain.Comment;
import java.util.List;

public interface CommentQueryRepository {

    Long countListByBoardId(Long boardId);

    List<Comment> findListByBoardId(Long boardId);
}
