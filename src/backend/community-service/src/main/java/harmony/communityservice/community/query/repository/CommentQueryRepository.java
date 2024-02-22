package harmony.communityservice.community.query.repository;

import harmony.communityservice.community.domain.Comment;
import java.util.Optional;

public interface CommentQueryRepository {

    Optional<Comment> findCommentById(Long commentId);
}
