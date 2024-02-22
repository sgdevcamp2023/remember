package harmony.communityservice.community.command.repository.jpa;

import harmony.communityservice.community.domain.Emoji;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEmojiCommandRepository extends JpaRepository<Emoji, Long> {
}
