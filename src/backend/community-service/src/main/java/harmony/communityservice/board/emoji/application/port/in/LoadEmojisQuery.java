package harmony.communityservice.board.emoji.application.port.in;

import harmony.communityservice.board.board.domain.Board.BoardId;

public interface LoadEmojisQuery {

    SearchEmojisResponse loadByBoardId(BoardId boardId);
}
