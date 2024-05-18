package harmony.communityservice.board.emoji.repository.command;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import harmony.communityservice.board.emoji.domain.Emoji;
import harmony.communityservice.board.emoji.domain.EmojiId;
import java.util.List;
import java.util.Optional;

public interface EmojiCommandRepository {
    void save(Emoji emoji);

    Optional<Emoji> findByBoardIdAndEmojiType(BoardIdJpaVO boardId, Long emojiType);

    void deleteById(EmojiId emojiId);

    void deleteListByBoardId(BoardIdJpaVO boardId);

    void deleteListByBoardIds(List<BoardIdJpaVO> boardIds);
}
