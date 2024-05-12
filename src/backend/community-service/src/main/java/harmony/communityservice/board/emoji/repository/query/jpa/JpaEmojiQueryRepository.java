package harmony.communityservice.board.emoji.repository.query.jpa;

import harmony.communityservice.board.board.domain.BoardId;
import harmony.communityservice.board.emoji.domain.Emoji;
import harmony.communityservice.board.emoji.domain.EmojiId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaEmojiQueryRepository extends JpaRepository<Emoji, EmojiId> {
    Optional<Emoji> findEmojiByBoardIdAndEmojiType(BoardId boardId, Long emojiType);

    @Query("select distinct e from Emoji e join fetch e.emojiUsers eu where e.boardId = :boardId")
    List<Emoji> findEmojisByBoardId(@Param("boardId") BoardId boardId);
}
