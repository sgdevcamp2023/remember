package harmony.communityservice.board.board.repository.command;

import harmony.communityservice.board.domain.Board;

public interface BoardCommandRepository {

    void save(Board board);

    void delete(Board board);
}
