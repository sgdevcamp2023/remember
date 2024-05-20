package harmony.communityservice.board.board.adapter.out.persistence;

import feign.Param;
import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelIdJpaVO;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface BoardQueryRepository extends JpaRepository<BoardEntity, BoardIdJpaVO> {

    @Query(value = "SELECT b FROM BoardEntity b "
            + "WHERE b.channelId = :channelId AND b.boardId < :lastBoardId "
            + "ORDER BY b.boardId DESC")
    List<BoardEntity> findBoardsByChannelOrderByBoardIdDesc(@Param("channelId") ChannelIdJpaVO channelId,
                                                            @Param("lastBoardId") BoardIdJpaVO lastBoardId,
                                                            Pageable pageable);
}
