package harmony.communityservice.board.emoji.service.query.impl;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import harmony.communityservice.board.emoji.domain.EmojiId;
import harmony.communityservice.board.emoji.repository.query.EmojiQueryRepository;
import harmony.communityservice.board.emoji.service.query.EmojiQueryService;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.board.emoji.domain.Emoji;
import harmony.communityservice.board.emoji.mapper.ToSearchEmojiResponseMapper;
import harmony.communityservice.board.emoji.dto.SearchEmojiResponse;
import harmony.communityservice.board.emoji.dto.SearchEmojisResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmojiQueryServiceImpl implements EmojiQueryService {

    private final EmojiQueryRepository emojiQueryRepository;

    @Override
    public Emoji searchByBoardIdAndEmojiType(Long boardId, Long emojiType) {
        return emojiQueryRepository.findByBoardAndEmojiType(BoardIdJpaVO.make(boardId), emojiType).orElse(null);
    }

    @Override
    public Emoji searchByEmojiId(Long emojiId) {
        return emojiQueryRepository.findByEmojiId(EmojiId.make(emojiId)).orElseThrow(NotFoundDataException::new);
    }

    @Override
    public SearchEmojisResponse searchListByBoardId(Long boardId) {
        List<Emoji> emojis = emojiQueryRepository.findEmojisByBoardId(BoardIdJpaVO.make(boardId));
        List<SearchEmojiResponse> searchEmojiResponses = emojis.stream()
                .map(ToSearchEmojiResponseMapper::convert).toList();
        return new SearchEmojisResponse(searchEmojiResponses);
    }
}
