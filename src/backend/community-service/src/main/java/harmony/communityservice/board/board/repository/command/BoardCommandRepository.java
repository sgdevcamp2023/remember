package harmony.communityservice.board.board.repository.command;

import harmony.communityservice.board.board.domain.Board;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardCommandRepository {

    void save(Board board);

    void delete(Board board);

    Optional<Board> findById(Long boardId);

    List<Long> findBoardIdsByChannelId(Long channelId);

    void deleteByChannelId(Long channelId);

    void deleteAllByChannelIds(@Param("channelIds") List<Long> channelIds);

    List<Long> findAllByChannelIds(@Param("channelIds") List<Long> channelIds);
}
