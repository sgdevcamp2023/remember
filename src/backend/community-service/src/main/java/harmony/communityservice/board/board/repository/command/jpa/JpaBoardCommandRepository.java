package harmony.communityservice.board.board.repository.command.jpa;

import harmony.communityservice.board.board.domain.Board;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaBoardCommandRepository extends JpaRepository<Board, Long> {


    @Query("select b.boardId from Board b where b.channelId = :channelId")
    List<Long> findIdsByChannelId(@Param("channelId") Long channelId);

    void deleteBoardsByChannelId(Long channelId);

    @Modifying
    @Query("delete from Board b where b.channelId in :channelIds")
    void deleteAllByChannelIds(@Param("channelIds") List<Long> channelIds);

    @Query("select b.boardId from Board b where b.channelId in :channelIds")
    List<Long> findAllByChannelIds(@Param("channelIds") List<Long> channelIds);
}
