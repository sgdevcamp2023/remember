package harmony.communityservice.board.emoji.application.port.out;

import harmony.communityservice.board.emoji.domain.Emoji.EmojiId;

public interface DeleteEmojiPort {

    void delete(EmojiId emojiId);
}
