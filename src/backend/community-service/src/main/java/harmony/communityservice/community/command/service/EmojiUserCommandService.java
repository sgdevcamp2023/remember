package harmony.communityservice.community.command.service;

import harmony.communityservice.community.domain.Emoji;
import harmony.communityservice.community.domain.EmojiUser;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface EmojiUserCommandService {
    void save(Emoji emoji, Long userId);

    void delete(EmojiUser emojiUser);
}
