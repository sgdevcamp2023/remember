package harmony.communityservice.board.emoji.repository.command;

import harmony.communityservice.board.board.domain.BoardId;
import harmony.communityservice.board.emoji.domain.Emoji;
import harmony.communityservice.board.emoji.domain.EmojiId;
import java.util.List;
import java.util.Optional;

public interface EmojiCommandRepository {
    void save(Emoji emoji);

    Optional<Emoji> findByBoardIdAndEmojiType(BoardId boardId, Long emojiType);

    void deleteById(EmojiId emojiId);

    void deleteListByBoardId(BoardId boardId);

    void deleteListByBoardIds(List<BoardId> boardIds);
}
