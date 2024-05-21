package harmony.communityservice.board.comment.application.port.out;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.comment.domain.Comment;
import java.util.List;

public interface LoadCommentsPort {

    List<Comment> loadByBoardId(BoardId boardId);
}
