package harmony.communityservice.community.query.repository.jpa;

import harmony.communityservice.community.domain.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCommentQueryRepository extends JpaRepository<Comment, Long> {

    Long countCommentsByBoardId(Long boardId);

    List<Comment> findCommentsByBoardId(Long boardId);
}
