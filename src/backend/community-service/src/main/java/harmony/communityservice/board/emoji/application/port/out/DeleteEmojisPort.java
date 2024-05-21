package harmony.communityservice.board.emoji.application.port.out;

import harmony.communityservice.board.board.domain.Board.BoardId;
import java.util.List;

public interface DeleteEmojisPort {
    void deleteByBoardId(BoardId boardId);

    void deleteByBoardIds(List<BoardId> boardIds);
}
