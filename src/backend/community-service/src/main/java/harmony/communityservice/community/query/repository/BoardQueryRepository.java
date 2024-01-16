package harmony.communityservice.community.query.repository;

import harmony.communityservice.community.domain.Board;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BoardQueryRepository {

    List<Board> findByChannelOrderByBoardId(Long channelId, Long lastBoardId, Pageable pageable);
}
