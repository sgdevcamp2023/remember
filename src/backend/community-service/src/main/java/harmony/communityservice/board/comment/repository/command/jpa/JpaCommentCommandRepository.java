package harmony.communityservice.board.comment.repository.command.jpa;

import harmony.communityservice.board.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCommentCommandRepository extends JpaRepository<Comment, Long> {

    void deleteCommentsByBoardId(Long boardId);
}
