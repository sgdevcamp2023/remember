package harmony.communityservice.community.query.repository;

import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.domain.Emoji;
import java.util.Optional;

public interface EmojiQueryRepository {
    Optional<Emoji> findByBoardAndEmojiType(Board board, Long emojiType);
}
