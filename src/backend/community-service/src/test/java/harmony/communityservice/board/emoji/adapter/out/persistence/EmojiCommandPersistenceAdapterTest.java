package harmony.communityservice.board.emoji.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.emoji.domain.Emoji.EmojiId;
import harmony.communityservice.common.exception.DuplicatedEmojiException;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.user.domain.User.UserId;
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
@Sql("EmojiCommandPersistenceAdapterTest.sql")
class EmojiCommandPersistenceAdapterTest {

    @Autowired
    EmojiCommandPersistenceAdapter emojiCommandPersistenceAdapter;

    @Autowired
    EmojiCommandRepository emojiCommandRepository;

    @Test
    @DisplayName("emoji 등록 테스트")
    void register_emoji() {
        emojiCommandPersistenceAdapter.register(BoardId.make(5L), UserId.make(1L), 1L);
        emojiCommandPersistenceAdapter.register(BoardId.make(5L), UserId.make(2L), 1L);
        emojiCommandPersistenceAdapter.register(BoardId.make(5L), UserId.make(3L), 1L);

        assertThrows(DuplicatedEmojiException.class,
                () -> emojiCommandPersistenceAdapter.register(BoardId.make(5L), UserId.make(1L), 1L));

        EmojiEntity emojiEntity = emojiCommandRepository.findByBoardIdAndEmojiType(BoardIdJpaVO.make(5L), 1L).get();

        assertEquals(emojiEntity.getEmojiUsers().size(), 3L);
        assertEquals(emojiEntity.getBoardId().getId(), 5L);
        assertEquals(emojiEntity.getEmojiType(), 1L);
    }

    @Test
    @DisplayName("emoji 삭제 테스트")
    void delete_emoji() {
        emojiCommandPersistenceAdapter.delete(EmojiId.make(1L));

        assertThrows(NotFoundDataException.class,
                () -> emojiCommandRepository.findById(EmojiIdJpaVO.make(1L)).orElseThrow(NotFoundDataException::new));
    }

    @Test
    @DisplayName("board안의 emoji List 삭제 테스트")
    void delete_emojis() {
        EmojiEntity firstEmojiEntity = emojiCommandRepository.findByBoardIdAndEmojiType(BoardIdJpaVO.make(1L), 1L)
                .orElse(null);
        EmojiEntity secondEmojiEntity = emojiCommandRepository.findByBoardIdAndEmojiType(BoardIdJpaVO.make(1L), 2L)
                .orElse(null);
        EmojiEntity thirdEmojiEntity = emojiCommandRepository.findByBoardIdAndEmojiType(BoardIdJpaVO.make(1L), 3L)
                .orElse(null);

        emojiCommandPersistenceAdapter.deleteByBoardId(BoardId.make(1L));

        assertAll(() -> {
            assertNotNull(firstEmojiEntity);
            assertNotNull(secondEmojiEntity);
            assertNotNull(thirdEmojiEntity);
            assertThrows(NotFoundDataException.class,
                    () -> emojiCommandRepository.findByBoardIdAndEmojiType(BoardIdJpaVO.make(1L), 1L)
                            .orElseThrow(NotFoundDataException::new));
            assertThrows(NotFoundDataException.class,
                    () -> emojiCommandRepository.findByBoardIdAndEmojiType(BoardIdJpaVO.make(1L), 2L)
                            .orElseThrow(NotFoundDataException::new));
            assertThrows(NotFoundDataException.class,
                    () -> emojiCommandRepository.findByBoardIdAndEmojiType(BoardIdJpaVO.make(1L), 3L)
                            .orElseThrow(NotFoundDataException::new));
        });
    }

    @Test
    @DisplayName("channel 안의 board들에 있는 Emoji List 삭제 테스트")
    void delete_boards_emojis() {
        List<EmojiEntity> emojiEntities = emojiCommandRepository.findAll();

        emojiCommandPersistenceAdapter.deleteByBoardIds(List.of(BoardId.make(1L), BoardId.make(2L), BoardId.make(3L)));

        List<EmojiEntity> result = emojiCommandRepository.findAll();

        assertThat(emojiEntities.size()).isNotZero();
        assertThat(result.size()).isZero();
    }
}