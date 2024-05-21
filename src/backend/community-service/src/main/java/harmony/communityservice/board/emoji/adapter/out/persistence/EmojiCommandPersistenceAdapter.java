package harmony.communityservice.board.emoji.adapter.out.persistence;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.emoji.application.port.out.DeleteEmojiPort;
import harmony.communityservice.board.emoji.application.port.out.DeleteEmojisPort;
import harmony.communityservice.board.emoji.application.port.out.RegisterEmojiPort;
import harmony.communityservice.board.emoji.domain.Emoji.EmojiId;
import harmony.communityservice.common.annotation.PersistenceAdapter;
import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class EmojiCommandPersistenceAdapter implements RegisterEmojiPort, DeleteEmojiPort, DeleteEmojisPort {

    private final EmojiCommandRepository emojiCommandRepository;

    @Override
    public void register(BoardId boardId, UserId userId, Long emojiType) {
        EmojiEntity targetEmoji = emojiCommandRepository.findByBoardIdAndEmojiType(
                BoardIdJpaVO.make(boardId.getId()), emojiType).orElse(null);

        if (targetEmoji == null) {
            notExistsEmoji(boardId, userId, emojiType);
        } else {
            targetEmoji.exists(UserIdJpaVO.make(userId.getId()));
        }
    }

    private void notExistsEmoji(BoardId boardId, UserId userId, Long emojiType) {
        EmojiEntity emoji = EmojiEntityMapper.convert(boardId, userId, emojiType);
        emojiCommandRepository.save(emoji);
    }

    @Override
    public void delete(EmojiId emojiId) {
        emojiCommandRepository.deleteById(EmojiIdJpaVO.make(emojiId.getId()));
    }

    @Override
    public void deleteByBoardId(BoardId boardId) {
        emojiCommandRepository.deleteEmojiUsersByBoardId(boardId.getId());
        emojiCommandRepository.deleteEmojisByBoardId(BoardIdJpaVO.make(boardId.getId()));
    }

    @Override
    public void deleteByBoardIds(List<BoardId> boardIds) {
        List<Long> ids = boardIds.stream()
                .map(BoardId::getId)
                .toList();
        List<BoardIdJpaVO> boardIdJpaVOS = boardIds.stream()
                .map(boardId -> BoardIdJpaVO.make(boardId.getId()))
                .toList();
        emojiCommandRepository.deleteEmojiUsersByBoardIds(ids);
        emojiCommandRepository.deleteEmojisByBoardIds(boardIdJpaVOS);
    }
}
