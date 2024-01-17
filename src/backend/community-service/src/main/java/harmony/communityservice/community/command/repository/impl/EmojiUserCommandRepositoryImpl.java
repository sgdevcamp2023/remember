package harmony.communityservice.community.command.repository.impl;

import harmony.communityservice.community.command.repository.EmojiUserCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaEmojiUserCommandRepository;
import harmony.communityservice.community.domain.EmojiUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmojiUserCommandRepositoryImpl implements EmojiUserCommandRepository {

    private final JpaEmojiUserCommandRepository jpaEmojiUserCommandRepository;

    @Override
    public void save(EmojiUser emojiUser) {
        jpaEmojiUserCommandRepository.save(emojiUser);
    }
}
