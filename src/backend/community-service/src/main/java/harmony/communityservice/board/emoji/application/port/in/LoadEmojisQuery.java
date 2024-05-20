package harmony.communityservice.board.emoji.application.port.in;

import harmony.communityservice.board.board.domain.Board.BoardId;

public interface LoadEmojisQuery {

    LoadEmojisResponse loadByBoardId(BoardId boardId);
}
