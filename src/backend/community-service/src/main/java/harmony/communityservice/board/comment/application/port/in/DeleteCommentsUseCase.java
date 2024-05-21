package harmony.communityservice.board.comment.application.port.in;

import harmony.communityservice.board.board.domain.Board.BoardId;
import java.util.List;

public interface DeleteCommentsUseCase {

    void deleteByBoardId(BoardId boardId);

    void deleteByBoardIds(List<BoardId> boardIds);
}
