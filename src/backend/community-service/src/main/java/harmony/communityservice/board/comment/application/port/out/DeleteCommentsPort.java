package harmony.communityservice.board.comment.application.port.out;

import harmony.communityservice.board.board.domain.Board.BoardId;
import java.util.List;

public interface DeleteCommentsPort {

    void deleteByBoardId(BoardId boardId);

    void deleteByBoardIds(List<BoardId> boardIds);
}
