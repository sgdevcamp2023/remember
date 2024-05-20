package harmony.communityservice.board.emoji.application.port.in;

import harmony.communityservice.board.board.domain.Board.BoardId;
import java.util.List;

public interface DeleteEmojisUseCase {
    void deleteByBoardId(BoardId boardId);

    void deleteByBoardIds(List<BoardId> boardIds);
}
