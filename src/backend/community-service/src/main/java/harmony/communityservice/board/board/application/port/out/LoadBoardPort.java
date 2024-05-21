package harmony.communityservice.board.board.application.port.out;

import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.Board.BoardId;

public interface LoadBoardPort {

    Board load(BoardId boardId);
}
