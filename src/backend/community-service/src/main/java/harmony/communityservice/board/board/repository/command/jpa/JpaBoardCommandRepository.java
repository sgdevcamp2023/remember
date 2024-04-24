package harmony.communityservice.board.board.repository.command.jpa;

import harmony.communityservice.board.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBoardCommandRepository extends JpaRepository<Board, Long> {

}
