package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.domain.Emoji;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface EmojiQueryService {
    Emoji findByBoardAndEmojiType(Board board, Long emojiType);

    Emoji findById(Long emojiId);
}
