package harmony.communityservice.board.emoji.repository.command.jpa;

import harmony.communityservice.board.domain.Emoji;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEmojiCommandRepository extends JpaRepository<Emoji, Long> {
}
