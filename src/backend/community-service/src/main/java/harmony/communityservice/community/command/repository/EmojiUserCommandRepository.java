package harmony.communityservice.community.command.repository;

import harmony.communityservice.community.domain.EmojiUser;

public interface EmojiUserCommandRepository {
    void save(EmojiUser emojiUser);
}
