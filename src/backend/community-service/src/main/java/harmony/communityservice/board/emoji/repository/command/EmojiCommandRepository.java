package harmony.communityservice.board.emoji.repository.command;

import harmony.communityservice.board.emoji.domain.Emoji;
import java.util.List;
import java.util.Optional;

public interface EmojiCommandRepository {
    void save(Emoji emoji);

    Optional<Emoji> findByBoardIdAndEmojiType(Long boardId, Long emojiType);

    void deleteById(Long emojiId);

    void deleteListByBoardId(Long boardId);

    void deleteListByBoardIds(List<Long> boardIds);
}
