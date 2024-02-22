package harmony.communityservice.community.command.repository.jpa;

import harmony.communityservice.community.domain.EmojiUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEmojiUserCommandRepository extends JpaRepository<EmojiUser, Long> {

}
