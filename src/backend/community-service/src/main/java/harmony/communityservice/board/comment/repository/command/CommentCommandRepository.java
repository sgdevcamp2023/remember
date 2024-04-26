package harmony.communityservice.board.comment.repository.command;

import harmony.communityservice.board.board.domain.BoardId;
import harmony.communityservice.board.comment.domain.Comment;
import harmony.communityservice.board.comment.domain.CommentId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

public interface CommentCommandRepository {

    void save(Comment comment);

    void deleteById(CommentId commentId);

    Optional<Comment> findById(CommentId commentId);

    void deleteListByBoardId(BoardId boardId);

    void deleteListByBoardIds(List<BoardId> boardIds);
}
