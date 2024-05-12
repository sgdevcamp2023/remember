package harmony.communityservice.board.board.repository.query.jpa;

import feign.Param;
import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.BoardId;
import harmony.communityservice.guild.channel.domain.ChannelId;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaBoardQueryRepository extends JpaRepository<Board, BoardId> {

    @Query(value = "SELECT distinct b FROM Board b join fetch b.images i WHERE b.channelId = :channelId AND b.boardId < :lastBoardId ORDER BY b.boardId DESC")
    List<Board> findBoardsByChannelOrderByBoardIdDesc(@Param("channelId") ChannelId channelId,
                                                      @Param("lastBoardId") BoardId lastBoardId,
                                                      Pageable pageable);
}
