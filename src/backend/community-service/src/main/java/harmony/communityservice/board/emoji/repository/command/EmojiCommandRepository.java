package harmony.communityservice.board.emoji.repository.command;

import harmony.communityservice.board.domain.Emoji;

public interface EmojiCommandRepository {
    void save(Emoji emoji);
}
