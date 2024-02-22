package harmony.communityservice.community.command.repository;

import harmony.communityservice.community.domain.Emoji;

public interface EmojiCommandRepository {
    void save(Emoji emoji);
}
