package harmony.communityservice.board.board.repository.command;

import harmony.communityservice.board.board.domain.Board;
import java.util.Optional;

public interface BoardCommandRepository {

    void save(Board board);

    void delete(Board board);

    Optional<Board> findById(Long boardId);
}
