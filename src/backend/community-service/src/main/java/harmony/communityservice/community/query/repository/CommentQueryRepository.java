package harmony.communityservice.community.query.repository;

import harmony.communityservice.community.domain.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentQueryRepository {

    Optional<Comment> findCommentById(Long commentId);

    Long countCommentsByBoardId(Long boardId);

    List<Comment> findCommentsByBoardId(Long boardId);
}
