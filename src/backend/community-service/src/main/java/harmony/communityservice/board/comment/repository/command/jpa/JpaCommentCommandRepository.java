package harmony.communityservice.board.comment.repository.command.jpa;

import harmony.communityservice.board.board.domain.BoardId;
import harmony.communityservice.board.comment.domain.Comment;
import harmony.communityservice.board.comment.domain.CommentId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaCommentCommandRepository extends JpaRepository<Comment, CommentId> {

    @Modifying
    @Query("delete from Comment c where c.boardId = :boardId")
    void deleteCommentsByBoardId(@Param("boardId") BoardId boardId);

    @Modifying
    @Query("delete from Comment c where c.boardId in :boardIds")
    void deleteAllByBoardIds(@Param("boardIds") List<BoardId> boardIds);
}
