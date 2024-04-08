package harmony.communityservice.board.emoji.repository.query.impl;

import harmony.communityservice.board.emoji.repository.query.EmojiQueryRepository;
import harmony.communityservice.board.emoji.repository.query.jpa.JpaEmojiQueryRepository;
import harmony.communityservice.board.domain.Emoji;
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
