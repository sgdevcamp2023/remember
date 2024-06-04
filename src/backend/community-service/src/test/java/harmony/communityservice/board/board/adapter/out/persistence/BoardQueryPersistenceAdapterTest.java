package harmony.communityservice.board.board.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelIdJpaVO;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@EnableTransactionManagement
@Sql("BoardQueryPersistenceAdapterTest.sql")
class BoardQueryPersistenceAdapterTest {

    private static final int MAX_PAGE_COUNT = 50;

    @Autowired
    BoardQueryPersistenceAdapter boardQueryPersistenceAdapter;

    @Autowired
    BoardQueryRepository boardQueryRepository;

    @Test
    @DisplayName("게시글 리스트 조회 테스트")
    void load_boards() {
        PageRequest pageRequest = PageRequest.of(0, MAX_PAGE_COUNT);
        List<Board> boards = boardQueryPersistenceAdapter.loadBoards(ChannelId.make(1L), BoardId.make(4L), pageRequest);
        Page<BoardEntity> boardEntities = boardQueryRepository.findBoardsByChannelOrderByBoardIdDesc(
                ChannelIdJpaVO.make(1L), BoardIdJpaVO.make(4L),
                pageRequest);
        ImageEntity firstImageEntity = boardEntities.getContent().get(0).getImages().get(0);
        ImageEntity secondImageEntity = boardEntities.getContent().get(0).getImages().get(1);
        assertEquals(boards.size(),3L);
        assertEquals(boards.size(),boardEntities.getContent().size());
        assertEquals(firstImageEntity.equals(secondImageEntity),false);
        assertEquals(firstImageEntity.equals(null), false);
        assertEquals(firstImageEntity.getUpdatedAt().toString(),"2024-05-27T15:48:38.152Z");
    }

    @Test
    @DisplayName("게시글 단건 조회 테스트")
    void load_board() {
        Board board = boardQueryPersistenceAdapter.load(BoardId.make(1L));
        BoardEntity boardEntity = boardQueryRepository.findById(BoardIdJpaVO.make(1L))
                .orElseThrow(NotFoundDataException::new);

        assertEquals(boardEntity.getImages().size(),board.getImages().size());
        assertThat(boardEntity.getImages().get(0).getId().getId()).isBetween(1L, 100L);
        assertThat(board.getImages().get(0).getImageId().getId()).isBetween(1L, 100L);
        assertEquals(boardEntity.getContent().getTitle(),board.getContent().getTitle());
        assertEquals(board.getBoardId().getId(),boardEntity.getId().getId());
    }

}