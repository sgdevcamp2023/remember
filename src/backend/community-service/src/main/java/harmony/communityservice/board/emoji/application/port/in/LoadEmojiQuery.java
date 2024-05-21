package harmony.communityservice.board.emoji.application.port.in;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.emoji.domain.Emoji;
import harmony.communityservice.board.emoji.domain.Emoji.EmojiId;

public interface LoadEmojiQuery {

    Emoji load(BoardId boardId, Long emojiType);

    Emoji loadById(EmojiId emojiId);
}
