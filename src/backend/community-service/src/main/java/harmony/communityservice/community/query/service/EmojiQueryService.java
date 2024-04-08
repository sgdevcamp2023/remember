package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.Emoji;
import harmony.communityservice.community.query.dto.SearchCommentsResponse;
import harmony.communityservice.community.query.dto.SearchEmojisResponse;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface EmojiQueryService {

    Emoji searchByBoardIdAndEmojiType(Long boardId, Long emojiType);

    Emoji searchByEmojiId(Long emojiId);

    SearchEmojisResponse searchListByBoardId(Long boardId);
}
