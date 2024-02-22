package harmony.communityservice.community.command.repository.impl;

import harmony.communityservice.community.command.repository.EmojiCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaEmojiCommandRepository;
import harmony.communityservice.community.domain.Emoji;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmojiCommandRepositoryImpl implements EmojiCommandRepository {

    private final JpaEmojiCommandRepository jpaEmojiCommandRepository;

    @Override
    public void save(Emoji emoji) {
        jpaEmojiCommandRepository.save(emoji);
    }
}
