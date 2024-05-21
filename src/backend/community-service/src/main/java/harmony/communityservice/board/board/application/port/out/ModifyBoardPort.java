package harmony.communityservice.board.board.application.port.out;

import harmony.communityservice.board.board.domain.Board.BoardId;

public interface ModifyBoardPort {

    void modify(Long writerId, BoardId boardId, String title, String content);
}
