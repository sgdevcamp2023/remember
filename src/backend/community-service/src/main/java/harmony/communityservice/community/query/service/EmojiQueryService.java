package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.domain.Emoji;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface EmojiQueryService {

    Emoji searchByBoardAndEmojiType(Board board, Long emojiType);

    Emoji searchByEmojiId(Long emojiId);
}
