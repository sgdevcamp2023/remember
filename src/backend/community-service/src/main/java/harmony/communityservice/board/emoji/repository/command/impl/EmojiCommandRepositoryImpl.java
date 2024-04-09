package harmony.communityservice.board.emoji.repository.command.impl;

import harmony.communityservice.board.emoji.repository.command.EmojiCommandRepository;
import harmony.communityservice.board.emoji.repository.command.jpa.JpaEmojiCommandRepository;
import harmony.communityservice.board.domain.Emoji;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmojiCommandRepositoryImpl implements EmojiCommandRepository {

    private final JpaEmojiCommandRepository jpaEmojiCommandRepository;

    @Override
    public void save(Emoji emoji) {
        jpaEmojiCommandRepository.save(emoji);
    }
}
