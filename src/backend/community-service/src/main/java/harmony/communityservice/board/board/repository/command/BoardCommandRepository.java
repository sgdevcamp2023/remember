package harmony.communityservice.board.board.repository.command;

import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.BoardId;
import harmony.communityservice.guild.channel.domain.ChannelId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardCommandRepository {

    void save(Board board);

    void delete(Board board);

    Optional<Board> findById(BoardId boardId);

    List<BoardId> findBoardIdsByChannelId(ChannelId channelId);

    void deleteByChannelId(ChannelId channelId);

    void deleteAllByChannelIds(@Param("channelIds") List<ChannelId> channelIds);

    List<BoardId> findAllByChannelIds(@Param("channelIds") List<ChannelId> channelIds);
}
