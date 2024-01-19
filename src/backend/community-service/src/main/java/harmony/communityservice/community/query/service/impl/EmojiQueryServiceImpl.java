package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.domain.Emoji;
import harmony.communityservice.community.query.repository.EmojiQueryRepository;
import harmony.communityservice.community.query.service.EmojiQueryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmojiQueryServiceImpl implements EmojiQueryService {

    private final EmojiQueryRepository emojiQueryRepository;

    @Override
    public Emoji findByBoardAndEmojiType(Board board, Long emojiType) {
        return emojiQueryRepository.findByBoardAndEmojiType(board,emojiType).orElse(null);
    }

    @Override
    public Emoji findById(Long emojiId) {
        return emojiQueryRepository.findByEmojiId(emojiId).orElseThrow(NotFoundDataException::new);
    }
}
