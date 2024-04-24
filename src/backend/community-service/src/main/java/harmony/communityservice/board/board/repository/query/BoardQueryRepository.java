package harmony.communityservice.board.board.repository.query;

import harmony.communityservice.board.board.domain.Board;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface BoardQueryRepository {

    List<Board> findByChannelOrderByBoardId(Long channelId, Long lastBoardId, Pageable pageable);

    Optional<Board> findByBoardId(Long boardId);
}
