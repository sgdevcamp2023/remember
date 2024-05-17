package harmony.communityservice.board.board.repository.query;

import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.BoardId;
import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelIdJpaVO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface BoardQueryRepository {

    List<Board> findByChannelOrderByBoardId(ChannelIdJpaVO channelId, BoardId lastBoardId, Pageable pageable);

    Optional<Board> findByBoardId(BoardId boardId);
}
