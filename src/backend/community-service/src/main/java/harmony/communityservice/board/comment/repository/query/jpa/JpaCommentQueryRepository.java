package harmony.communityservice.board.comment.repository.query.jpa;

import harmony.communityservice.board.comment.domain.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCommentQueryRepository extends JpaRepository<Comment, Long> {

    Long countCommentsByBoardId(Long boardId);

    List<Comment> findCommentsByBoardId(Long boardId);
}
