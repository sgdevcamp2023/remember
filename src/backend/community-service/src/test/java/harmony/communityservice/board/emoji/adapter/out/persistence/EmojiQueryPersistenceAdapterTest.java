package harmony.communityservice.board.emoji.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.emoji.domain.Emoji;
import harmony.communityservice.board.emoji.domain.Emoji.EmojiId;
import harmony.communityservice.common.exception.NotFoundDataException;
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
@Sql("EmojiQueryPersistenceAdapterTest.sql")
class EmojiQueryPersistenceAdapterTest {

    @Autowired
    EmojiQueryPersistenceAdapter emojiQueryPersistenceAdapter;

    @Autowired
    EmojiQueryRepository emojiQueryRepository;

    @Test
    @DisplayName("이모지 조회 테스트")
    void load_emoji() {
        Emoji emoji = emojiQueryPersistenceAdapter.load(EmojiId.make(6L));

        EmojiEntity emojiEntity = emojiQueryRepository.findByEmojiId(EmojiIdJpaVO.make(6L))
                .orElseThrow(NotFoundDataException::new);

        assertEquals(emoji.getEmojiType(), emojiEntity.getEmojiType());
        assertEquals(emoji.getEmojiUsers().size(), emojiEntity.getEmojiUsers().size());
    }

    @Test
    @DisplayName("이모지 조회 테스트2")
    void load_emoji_board_id() {
        Emoji emoji = emojiQueryPersistenceAdapter.loadByBoardIdAndEmojiType(BoardId.make(1L), 1L);
        EmojiEntity emojiEntity = emojiQueryRepository.findEmojiByBoardIdAndEmojiType(BoardIdJpaVO.make(1L), 1L)
                .orElseThrow(NotFoundDataException::new);
        
        assertEquals(emoji.getEmojiType(), emojiEntity.getEmojiType());
        assertEquals(emoji.getEmojiUsers().size(), emojiEntity.getEmojiUsers().size());
    }

    @Test
    @DisplayName("게시글 안의 이모지 조회 테스트")
    void load_board_emojis() {
        List<Emoji> emojis = emojiQueryPersistenceAdapter.loadEmojisByBoardId(BoardId.make(1L));
        List<EmojiEntity> emojiEntities = emojiQueryRepository.findEmojisByBoardId(BoardIdJpaVO.make(1L));
        assertEquals(emojis.size(),3L);
        assertEquals(emojiEntities.size(),emojis.size());
    }
}