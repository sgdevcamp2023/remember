package harmony.communityservice.community.query.repository.jpa;

import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.domain.Emoji;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEmojiQueryRepository extends JpaRepository<Emoji, Long> {
    Optional<Emoji> findEmojiByBoardAndEmojiType(Board board, Long emojiType);
}
