package harmony.communityservice.board.emoji.repository.command.impl;

import harmony.communityservice.board.board.domain.BoardId;
import harmony.communityservice.board.emoji.domain.Emoji;
import harmony.communityservice.board.emoji.domain.EmojiId;
import harmony.communityservice.board.emoji.repository.command.EmojiCommandRepository;
import harmony.communityservice.board.emoji.repository.command.jpa.JpaEmojiCommandRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmojiCommandRepositoryImpl implements EmojiCommandRepository {

    private final JpaEmojiCommandRepository jpaEmojiCommandRepository;

    @Override
    public void save(Emoji emoji) {
        jpaEmojiCommandRepository.save(emoji);
    }

    @Override
    public Optional<Emoji> findByBoardIdAndEmojiType(BoardId boardId, Long emojiType) {
        return jpaEmojiCommandRepository.findByBoardIdAndEmojiType(boardId, emojiType);
    }

    @Override
    public void deleteById(EmojiId emojiId) {
        jpaEmojiCommandRepository.deleteById(emojiId);
    }

    @Override
    public void deleteListByBoardId(BoardId boardId) {
        jpaEmojiCommandRepository.deleteEmojisByBoardId(boardId);
    }

    @Override
    public void deleteListByBoardIds(List<BoardId> boardIds) {
        jpaEmojiCommandRepository.deleteEmojisByBoardIds(boardIds);
    }
}
