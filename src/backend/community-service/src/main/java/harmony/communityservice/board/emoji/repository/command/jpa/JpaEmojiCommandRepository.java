package harmony.communityservice.board.emoji.repository.command.jpa;

import harmony.communityservice.board.domain.Emoji;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEmojiCommandRepository extends JpaRepository<Emoji, Long> {

    Optional<Emoji> findByBoardIdAndEmojiType(Long boardId, Long emojiType);

    void deleteEmojisByBoardId(Long boardId);
}
