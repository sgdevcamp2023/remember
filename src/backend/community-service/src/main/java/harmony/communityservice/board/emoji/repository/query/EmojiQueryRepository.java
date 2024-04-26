package harmony.communityservice.board.emoji.repository.query;

import harmony.communityservice.board.board.domain.BoardId;
import harmony.communityservice.board.emoji.domain.Emoji;
import harmony.communityservice.board.emoji.domain.EmojiId;
import java.util.List;
import java.util.Optional;

public interface EmojiQueryRepository {
    Optional<Emoji> findByBoardAndEmojiType(BoardId boardId, Long emojiType);

    Optional<Emoji> findByEmojiId(EmojiId emojiId);

    List<Emoji> findEmojisByBoardId(BoardId boardId);
}
