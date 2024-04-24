package harmony.communityservice.board.emoji.service.query;

import harmony.communityservice.board.emoji.domain.Emoji;
import harmony.communityservice.board.emoji.dto.SearchEmojisResponse;

public interface EmojiQueryService {

    Emoji searchByBoardIdAndEmojiType(Long boardId, Long emojiType);

    Emoji searchByEmojiId(Long emojiId);

    SearchEmojisResponse searchListByBoardId(Long boardId);
}
