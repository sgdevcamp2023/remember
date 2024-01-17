package harmony.communityservice.community.query.repository;

import harmony.communityservice.community.domain.Board;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface BoardQueryRepository {

    List<Board> findByChannelOrderByBoardId(Long channelId, Long lastBoardId, Pageable pageable);

    Optional<Board> findByBoardId(Long boardId);
}
