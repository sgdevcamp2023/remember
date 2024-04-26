package harmony.communityservice.board.board.repository.command.jpa;

import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.BoardId;
import harmony.communityservice.guild.channel.domain.ChannelId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaBoardCommandRepository extends JpaRepository<Board, BoardId> {


    @Query("select b.boardId from Board b where b.channelId = :channelId")
    List<BoardId> findIdsByChannelId(@Param("channelId") ChannelId channelId);

    @Modifying
    @Query("delete from Board b where b.channelId = :channelId")
    void deleteBoardsByChannelId(ChannelId channelId);

    @Modifying
    @Query("delete from Board b where b.channelId in :channelIds")
    void deleteAllByChannelIds(@Param("channelIds") List<ChannelId> channelIds);

    @Query("select b.boardId from Board b where b.channelId in :channelIds")
    List<BoardId> findAllByChannelIds(@Param("channelIds") List<ChannelId> channelIds);
}
