package harmony.communityservice.board.comment.repository.command;

import harmony.communityservice.board.comment.domain.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

public interface CommentCommandRepository {

    void save(Comment comment);

    void deleteById(Long commentId);

    Optional<Comment> findById(Long commentId);

    void deleteListByBoardId(Long boardId);

    void deleteListByBoardIds(List<Long> boardIds);
}
