package harmony.communityservice.board.board.application.port.out;

import harmony.communityservice.board.board.domain.Board;

public interface RegisterBoardPort {

    void register(Board board);
}
