package harmony.communityservice.board.comment.adapter.out.persistence;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import harmony.communityservice.common.domain.ModifiedType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface CommentCommandRepository extends JpaRepository<CommentEntity, CommentIdJpaVO> {

    @Modifying
    @Query("delete from CommentEntity c where c.boardId = :boardId")
    void deleteCommentsByBoardId(@Param("boardId") BoardIdJpaVO boardId);

    @Modifying
    @Query("delete from CommentEntity c where c.boardId in :boardIds")
    void deleteAllByBoardIds(@Param("boardIds") List<BoardIdJpaVO> boardIds);

    @Modifying
    @Query("update CommentEntity c set c.comment = :comment, c.type = :type "
            + "where c.commentId = :commentId and c.writerInfo.writerId = :writerId")
    void updateComment(@Param("comment") String comment, @Param("type") ModifiedType modifiedType,
                       @Param("commentId") CommentIdJpaVO commentIdJpaVO, @Param("writerId") Long writerId);
}

