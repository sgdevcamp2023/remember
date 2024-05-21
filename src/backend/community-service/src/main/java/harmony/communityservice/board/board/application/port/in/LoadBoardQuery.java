package harmony.communityservice.board.board.application.port.in;

import harmony.communityservice.board.board.domain.Board.BoardId;

public interface LoadBoardQuery {
    LoadBoardDetailResponse loadDetail(BoardId boardId);
}
