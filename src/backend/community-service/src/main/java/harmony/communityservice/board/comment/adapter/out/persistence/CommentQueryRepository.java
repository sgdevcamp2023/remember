package harmony.communityservice.board.comment.adapter.out.persistence;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface CommentQueryRepository extends JpaRepository<CommentEntity, CommentIdJpaVO> {

    Long countCommentsByBoardId(BoardIdJpaVO boardId);

    @Query("select c from CommentEntity c where c.boardId = :boardId")
    List<CommentEntity> findCommentsByBoardId(@Param("boardId") BoardIdJpaVO boardId);
}
