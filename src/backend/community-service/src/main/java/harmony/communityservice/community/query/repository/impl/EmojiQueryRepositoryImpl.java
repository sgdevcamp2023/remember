package harmony.communityservice.community.query.repository.impl;

import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.domain.Emoji;
import harmony.communityservice.community.query.repository.EmojiQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaEmojiQueryRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmojiQueryRepositoryImpl implements EmojiQueryRepository {

    private final JpaEmojiQueryRepository jpaEmojiQueryRepository;

    @Override
    public Optional<Emoji> findByBoardAndEmojiType(Board board, Long emojiType) {
        return jpaEmojiQueryRepository.findEmojiByBoardAndEmojiType(board, emojiType);
    }
}
