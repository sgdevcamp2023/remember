package harmony.communityservice.community.command.service;

import harmony.communityservice.community.domain.Emoji;

public interface EmojiUserCommandService {
    void save(Emoji emoji, Long userId);
}
