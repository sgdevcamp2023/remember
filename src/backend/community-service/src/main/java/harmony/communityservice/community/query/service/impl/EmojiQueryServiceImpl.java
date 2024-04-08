package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.community.domain.Emoji;
import harmony.communityservice.community.mapper.ToSearchEmojiResponseMapper;
import harmony.communityservice.community.query.dto.SearchEmojiResponse;
import harmony.communityservice.community.query.dto.SearchEmojisResponse;
import harmony.communityservice.community.query.repository.EmojiQueryRepository;
import harmony.communityservice.community.query.service.EmojiQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmojiQueryServiceImpl implements EmojiQueryService {

    private final EmojiQueryRepository emojiQueryRepository;

    @Override
    public Emoji searchByBoardIdAndEmojiType(Long boardId, Long emojiType) {
        return emojiQueryRepository.findByBoardAndEmojiType(boardId, emojiType).orElse(null);
    }

    @Override
    public Emoji searchByEmojiId(Long emojiId) {
        return emojiQueryRepository.findByEmojiId(emojiId).orElseThrow(NotFoundDataException::new);
    }

    @Override
    public SearchEmojisResponse searchListByBoardId(Long boardId) {
        List<Emoji> emojis = emojiQueryRepository.findEmojisByBoardId(boardId);
        List<SearchEmojiResponse> searchEmojiResponses = emojis.stream()
                .map(ToSearchEmojiResponseMapper::convert).toList();
        return new SearchEmojisResponse(searchEmojiResponses);
    }
}
