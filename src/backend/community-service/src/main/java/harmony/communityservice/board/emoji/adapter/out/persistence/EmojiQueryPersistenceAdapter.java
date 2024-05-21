package harmony.communityservice.board.emoji.adapter.out.persistence;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.emoji.application.port.out.LoadEmojiPort;
import harmony.communityservice.board.emoji.application.port.out.LoadEmojisPort;
import harmony.communityservice.board.emoji.domain.Emoji;
import harmony.communityservice.board.emoji.domain.Emoji.EmojiId;
import harmony.communityservice.common.annotation.PersistenceAdapter;
import harmony.communityservice.common.exception.NotFoundDataException;
import java.util.List;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class EmojiQueryPersistenceAdapter implements LoadEmojiPort, LoadEmojisPort {

    private final EmojiQueryRepository emojiQueryRepository;

    @Override
    public Emoji loadByBoardIdAndEmojiType(BoardId boardId, Long emojiType) {
        EmojiEntity emojiEntity = emojiQueryRepository.findEmojiByBoardIdAndEmojiType(
                BoardIdJpaVO.make(boardId.getId()), emojiType).orElse(null);

        return EmojiMapper.convert(emojiEntity);
    }

    @Override
    public Emoji load(EmojiId emojiId) {
        EmojiEntity emojiEntity = emojiQueryRepository.findByEmojiId(EmojiIdJpaVO.make(emojiId.getId()))
                .orElseThrow(NotFoundDataException::new);
        return EmojiMapper.convert(emojiEntity);
    }

    @Override
    public List<Emoji> loadEmojisByBoardId(BoardId boardId) {
        List<EmojiEntity> emojiEntities = emojiQueryRepository.findEmojisByBoardId(
                BoardIdJpaVO.make(boardId.getId()));
        return emojiEntities.stream()
                .map(EmojiMapper::convert)
                .toList();
    }
}
