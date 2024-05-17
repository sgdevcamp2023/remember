package harmony.communityservice.board.board.repository.command;

import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.BoardId;
import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelIdJpaVO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

public interface BoardCommandRepository {

    void save(Board board);

    void delete(Board board);

    Optional<Board> findById(BoardId boardId);

    List<BoardId> findBoardIdsByChannelId(ChannelIdJpaVO channelId);

    void deleteByChannelId(ChannelIdJpaVO channelId);

    void deleteAllByChannelIds(@Param("channelIds") List<ChannelIdJpaVO> channelIds);

    List<BoardId> findAllByChannelIds(@Param("channelIds") List<ChannelIdJpaVO> channelIds);
}
