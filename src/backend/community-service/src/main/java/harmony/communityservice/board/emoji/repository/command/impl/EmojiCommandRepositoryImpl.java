package harmony.communityservice.board.emoji.repository.command.impl;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
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
    public Optional<Emoji> findByBoardIdAndEmojiType(BoardIdJpaVO boardId, Long emojiType) {
        return jpaEmojiCommandRepository.findByBoardIdAndEmojiType(boardId, emojiType);
    }

    @Override
    public void deleteById(EmojiId emojiId) {
        jpaEmojiCommandRepository.deleteById(emojiId);
    }

    @Override
    public void deleteListByBoardId(BoardIdJpaVO boardId) {
        jpaEmojiCommandRepository.deleteEmojiUsersByBoardId(boardId.getId());
        jpaEmojiCommandRepository.deleteEmojisByBoardId(boardId);
    }

    @Override
    public void deleteListByBoardIds(List<BoardIdJpaVO> boardIds) {
        jpaEmojiCommandRepository.deleteEmojiUsersByBoardIds(boardIds.stream().map(BoardIdJpaVO::getId).toList());
        jpaEmojiCommandRepository.deleteEmojisByBoardIds(boardIds);
    }
}
