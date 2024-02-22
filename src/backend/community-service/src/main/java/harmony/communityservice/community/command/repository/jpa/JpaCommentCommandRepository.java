package harmony.communityservice.community.command.repository.jpa;

import harmony.communityservice.community.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCommentCommandRepository extends JpaRepository<Comment, Long> {
}
