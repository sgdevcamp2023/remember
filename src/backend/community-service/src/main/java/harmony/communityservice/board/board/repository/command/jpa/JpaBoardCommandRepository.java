package harmony.communityservice.board.board.repository.command.jpa;

import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.BoardId;
import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelIdJpaVO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaBoardCommandRepository extends JpaRepository<Board, BoardId> {


    @Query("select b.boardId from Board b where b.channelId = :channelId")
    List<BoardId> findIdsByChannelId(@Param("channelId") ChannelIdJpaVO channelId);

    @Modifying
    @Query(value = "delete from image as i where i.board_id in "
            + "(select b.board_id from board as b "
            + "where b.channel_id = :channelId)",nativeQuery = true)
    void deleteImagesByChannelId(Long channelId);

    @Modifying
    @Query("delete from Board b where b.channelId = :channelId")
    void deleteBoardsByChannelId(ChannelIdJpaVO channelId);

    @Modifying
    @Query(value = "delete from image as i where i.board_id in "
            + "(select b.board_id from board as b "
            + "where b.channel_id in :channelIds)",nativeQuery = true)
    void deleteImagesByChannelIds(List<Long> channelIds);

    @Modifying
    @Query("delete from Board b where b.channelId in :channelIds")
    void deleteAllByChannelIds(@Param("channelIds") List<ChannelIdJpaVO> channelIds);

    @Query("select b.boardId from Board b where b.channelId in :channelIds")
    List<BoardId> findAllByChannelIds(@Param("channelIds") List<ChannelIdJpaVO> channelIds);
}
