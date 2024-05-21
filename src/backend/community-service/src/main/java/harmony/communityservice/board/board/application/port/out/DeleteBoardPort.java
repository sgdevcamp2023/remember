package harmony.communityservice.board.board.application.port.out;

import harmony.communityservice.board.board.domain.Board.BoardId;

public interface DeleteBoardPort {

    void delete(Long writerId, BoardId boardId);
}
