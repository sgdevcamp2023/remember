package harmony.communityservice.board.board.repository.query.jpa;

import feign.Param;
import harmony.communityservice.board.domain.Board;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaBoardQueryRepository extends JpaRepository<Board, Long> {

    @Query(value = "SELECT b FROM Board b WHERE b.channelId = :channelId AND b.boardId < :lastBoardId ORDER BY b.boardId DESC")
    List<Board> findBoardsByChannelOrderByBoardIdDesc(@Param("channelId") Long channelId,
                                                      @Param("lastBoardId") Long lastBoardId,
                                                      Pageable pageable);
}
