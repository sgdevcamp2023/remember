package harmony.communityservice.community.query.repository;

import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.domain.Emoji;
import java.util.List;
import java.util.Optional;

public interface EmojiQueryRepository {
    Optional<Emoji> findByBoardAndEmojiType(Long boardId, Long emojiType);

    Optional<Emoji> findByEmojiId(Long emojiId);

    List<Emoji> findEmojisByBoardId(Long boardId);
}
