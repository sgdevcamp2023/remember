package harmony.communityservice.board.board.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.board.domain.Image;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.generic.WriterInfoJpaVO;
import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelIdJpaVO;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@EnableTransactionManagement
@Sql("BoardCommandPersistenceAdapterTest.sql")
class BoardCommandPersistenceAdapterTest {

    @Autowired
    BoardCommandPersistenceAdapter boardCommandPersistenceAdapter;

    @Autowired
    BoardCommandRepository boardCommandRepository;

    @Test
    @DisplayName("게시글 저장 테스트")
    void register_board() {
        Image image = Image.make("first");
        ImageEntity imageEntity = ImageEntity.make("first");
        Board board = Board.builder()
                .title("first_board")
                .content("first_content")
                .channelId(ChannelId.make(1L))
                .writerId(1L)
                .username("user")
                .profile("first_profile")
                .images(List.of(image))
                .build();

        BoardEntity entity = BoardEntity.builder()
                .title("first_board")
                .content("first_content")
                .channelId(ChannelIdJpaVO.make(1L))
                .writerId(1L)
                .writerName("user")
                .writerProfile("first_profile")
                .images(List.of(imageEntity))
                .build();
        ContentJpaVO testContent = ContentJpaVO.make("test", "test");

        boardCommandPersistenceAdapter.register(board);
        BoardEntity boardEntity = boardCommandRepository.findById(BoardIdJpaVO.make(5L))
                .orElseThrow(NotFoundDataException::new);
        ContentJpaVO content = boardEntity.getContent();
        WriterInfoJpaVO writerInfo = boardEntity.getWriterInfo();

        boolean equals = content.equals(null);
        assertEquals(equals, false);
        assertEquals(content.equals(testContent),false);
        assertEquals(content.equals(writerInfo), false);
        assertEquals(boardEntity.getContent().getContent(), board.getContent().getContent());
        assertNotEquals(boardEntity, entity);
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void modify_board() {
        boardCommandPersistenceAdapter.modify(1L, BoardId.make(1L), "modify_title", "modify_content");

        BoardEntity boardEntity = boardCommandRepository.findById(BoardIdJpaVO.make(1L))
                .orElseThrow(NotFoundDataException::new);

        assertAll(() -> {
            assertEquals(boardEntity.getContent().getContent(), "modify_content");
            assertEquals(boardEntity.getContent().getTitle(), "modify_title");
            assertEquals(boardEntity.getBoardId().getId(), 1L);
        });
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void delete_board() {
        boardCommandPersistenceAdapter.delete(1L, BoardId.make(1L));

        assertThrows(NotFoundDataException.class,
                () -> boardCommandRepository.findById(BoardIdJpaVO.make(1L)).orElseThrow(NotFoundDataException::new));
    }

    @Test
    @DisplayName("채널안에 있는 게시글 삭제 테스트")
    void delete_channel_boards() {
        List<BoardEntity> boardEntities = boardCommandRepository.findAll().stream()
                .filter(boardEntity -> boardEntity.getChannelId().getId().equals(1L))
                .toList();
        List<BoardId> boardIds = boardCommandPersistenceAdapter.deleteChannelBoards(ChannelId.make(1L));
        List<BoardEntity> result = boardCommandRepository.findAll().stream()
                .filter(boardEntity -> boardEntity.getChannelId().getId().equals(1L))
                .toList();
        assertEquals(boardEntities.size(), boardIds.size());
        assertEquals(result.size(), 0L);
    }

    @Test
    @DisplayName("길드안에 있는 게시글 삭제 테스트")
    void delete_guild_boards() {
        List<ChannelId> channelIds = List.of(ChannelId.make(1L), ChannelId.make(2L));
        List<BoardEntity> boardEntities = boardCommandRepository.findAll().stream()
                .filter(boardEntity -> channelIds.stream()
                        .anyMatch(channelId -> channelId.getId().equals(boardEntity.getChannelId().getId())))
                .toList();
        List<BoardId> boardIds = boardCommandPersistenceAdapter.deleteInChannels(channelIds);
        List<BoardEntity> result = boardCommandRepository.findAll().stream()
                .filter(boardEntity -> channelIds.stream()
                        .anyMatch(channelId -> channelId.getId().equals(boardEntity.getChannelId().getId())))
                .toList();
        assertEquals(boardEntities.size(), boardIds.size());
        assertEquals(result.size(), 0L);
    }

}