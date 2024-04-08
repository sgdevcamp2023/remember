package harmony.communityservice.community.query.repository.impl;

import harmony.communityservice.community.domain.Emoji;
import harmony.communityservice.community.query.repository.EmojiQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaEmojiQueryRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmojiQueryRepositoryImpl implements EmojiQueryRepository {

    private final JpaEmojiQueryRepository jpaEmojiQueryRepository;

    @Override
    public Optional<Emoji> findByBoardAndEmojiType(Long boardId, Long emojiType) {
        return jpaEmojiQueryRepository.findEmojiByBoardIdAndEmojiType(boardId, emojiType);
    }

    @Override
    public Optional<Emoji> findByEmojiId(Long emojiId) {
        return jpaEmojiQueryRepository.findById(emojiId);
    }

    @Override
    public List<Emoji> findEmojisByBoardId(Long boardId) {
        return jpaEmojiQueryRepository.findEmojisByBoardId(boardId);
    }
}
