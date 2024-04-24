package harmony.communityservice.board.emoji.repository.query.jpa;

import harmony.communityservice.board.emoji.domain.Emoji;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEmojiQueryRepository extends JpaRepository<Emoji, Long> {
    Optional<Emoji> findEmojiByBoardIdAndEmojiType(Long boardId, Long emojiType);

    List<Emoji> findEmojisByBoardId(Long boardId);
}
