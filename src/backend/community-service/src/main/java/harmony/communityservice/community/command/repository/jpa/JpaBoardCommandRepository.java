package harmony.communityservice.community.command.repository.jpa;

import harmony.communityservice.community.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBoardCommandRepository extends JpaRepository<Board, Long> {

}
