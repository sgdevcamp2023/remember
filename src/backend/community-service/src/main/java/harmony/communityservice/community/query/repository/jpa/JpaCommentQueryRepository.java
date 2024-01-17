package harmony.communityservice.community.query.repository.jpa;

import harmony.communityservice.community.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCommentQueryRepository extends JpaRepository<Comment, Long> {
}
