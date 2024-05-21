package harmony.communityservice.board.emoji.application.port.out;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.emoji.domain.Emoji;
import harmony.communityservice.board.emoji.domain.Emoji.EmojiId;

public interface LoadEmojiPort {
    Emoji loadByBoardIdAndEmojiType(BoardId boardId, Long emojiType);

    Emoji load(EmojiId emojiId);
}
