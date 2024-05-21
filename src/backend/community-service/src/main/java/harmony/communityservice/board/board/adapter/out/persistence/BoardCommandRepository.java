package harmony.communityservice.board.board.adapter.out.persistence;

import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelIdJpaVO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface BoardCommandRepository extends JpaRepository<BoardEntity, BoardIdJpaVO> {


    @Query("select b.boardId from BoardEntity b where b.channelId = :channelId")
    List<BoardIdJpaVO> findIdsByChannelId(@Param("channelId") ChannelIdJpaVO channelId);

    @Modifying
    @Query(value = "delete from image as i where i.board_id in "
            + "(select b.board_id from board as b "
            + "where b.channel_id = :channelId)", nativeQuery = true)
    void deleteImagesByChannelId(Long channelId);

    @Modifying
    @Query("delete from BoardEntity b where b.channelId = :channelId")
    void deleteBoardsByChannelId(ChannelIdJpaVO channelId);

    @Modifying
    @Query("delete from BoardEntity b where b.boardId = :boardId and b.writerInfo.writerId = :writerId")
    void deleteByBoardId(@Param("boardId") BoardIdJpaVO boardId, @Param("writerId") Long writerId);

    @Modifying
    @Query(value = "delete from image as i where i.board_id = :boardId", nativeQuery = true)
    void deleteImagesByBoardId(@Param("boardId") Long boardId);

    @Modifying
    @Query(value = "delete from image as i where i.board_id in "
            + "(select b.board_id from board as b "
            + "where b.channel_id in :channelIds)", nativeQuery = true)
    void deleteImagesByChannelIds(List<Long> channelIds);

    @Modifying
    @Query("delete from BoardEntity b where b.channelId in :channelIds")
    void deleteAllByChannelIds(@Param("channelIds") List<ChannelIdJpaVO> channelIds);

    @Query("select b.boardId from BoardEntity b where b.channelId in :channelIds")
    List<BoardIdJpaVO> findAllByChannelIds(@Param("channelIds") List<ChannelIdJpaVO> channelIds);

    @Modifying
    @Query("update BoardEntity b "
            + "set b.content.title = :title, b.content.content = :content"
            + " where b.boardId = :boardId and b.writerInfo.writerId = :writerId")
    void modifyContent(@Param("title") String title, @Param("content") String content,
                       @Param("boardId") BoardIdJpaVO boardId, @Param("writerId") Long writerId);
}
