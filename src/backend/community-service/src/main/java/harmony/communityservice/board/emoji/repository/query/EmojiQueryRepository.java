package harmony.communityservice.board.emoji.repository.query;

import harmony.communityservice.board.domain.Emoji;
import java.util.List;
import java.util.Optional;

public interface EmojiQueryRepository {
    Optional<Emoji> findByBoardAndEmojiType(Long boardId, Long emojiType);

    Optional<Emoji> findByEmojiId(Long emojiId);

    List<Emoji> findEmojisByBoardId(Long boardId);
}
