package harmony.communityservice.board.emoji.repository.command.jpa;

import harmony.communityservice.board.emoji.domain.Emoji;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface JpaEmojiCommandRepository extends JpaRepository<Emoji, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_READ)
    Optional<Emoji> findByBoardIdAndEmojiType(Long boardId, Long emojiType);

    void deleteEmojisByBoardId(Long boardId);
}
