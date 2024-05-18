package harmony.communityservice.board.emoji.repository.query;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import harmony.communityservice.board.emoji.domain.Emoji;
import harmony.communityservice.board.emoji.domain.EmojiId;
import java.util.List;
import java.util.Optional;

public interface EmojiQueryRepository {
    Optional<Emoji> findByBoardAndEmojiType(BoardIdJpaVO boardId, Long emojiType);

    Optional<Emoji> findByEmojiId(EmojiId emojiId);

    List<Emoji> findEmojisByBoardId(BoardIdJpaVO boardId);
}
