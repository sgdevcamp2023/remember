package harmony.communityservice.board.emoji.repository.query.impl;

import harmony.communityservice.board.board.domain.BoardId;
import harmony.communityservice.board.emoji.domain.Emoji;
import harmony.communityservice.board.emoji.domain.EmojiId;
import harmony.communityservice.board.emoji.repository.query.EmojiQueryRepository;
import harmony.communityservice.board.emoji.repository.query.jpa.JpaEmojiQueryRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmojiQueryRepositoryImpl implements EmojiQueryRepository {

    private final JpaEmojiQueryRepository jpaEmojiQueryRepository;

    @Override
    public Optional<Emoji> findByBoardAndEmojiType(BoardId boardId, Long emojiType) {
        return jpaEmojiQueryRepository.findEmojiByBoardIdAndEmojiType(boardId, emojiType);
    }

    @Override
    public Optional<Emoji> findByEmojiId(EmojiId emojiId) {
        return jpaEmojiQueryRepository.findById(emojiId);
    }

    @Override
    public List<Emoji> findEmojisByBoardId(BoardId boardId) {
        return jpaEmojiQueryRepository.findEmojisByBoardId(boardId);
    }
}
