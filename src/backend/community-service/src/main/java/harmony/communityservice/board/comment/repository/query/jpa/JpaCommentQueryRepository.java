package harmony.communityservice.board.comment.repository.query.jpa;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import harmony.communityservice.board.comment.domain.Comment;
import harmony.communityservice.board.comment.domain.CommentId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaCommentQueryRepository extends JpaRepository<Comment, CommentId> {

    Long countCommentsByBoardId(BoardIdJpaVO boardId);

    @Query("select c from Comment c where c.boardId = :boardId")
    List<Comment> findCommentsByBoardId(@Param("boardId") BoardIdJpaVO boardId);
}
