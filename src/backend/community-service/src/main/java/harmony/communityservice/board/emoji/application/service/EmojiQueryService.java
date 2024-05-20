package harmony.communityservice.board.emoji.application.service;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.emoji.application.port.in.LoadEmojiQuery;
import harmony.communityservice.board.emoji.application.port.in.LoadEmojisQuery;
import harmony.communityservice.board.emoji.application.port.in.SearchEmojiResponse;
import harmony.communityservice.board.emoji.application.port.in.SearchEmojisResponse;
import harmony.communityservice.board.emoji.application.port.out.LoadEmojiPort;
import harmony.communityservice.board.emoji.application.port.out.LoadEmojisPort;
import harmony.communityservice.board.emoji.domain.Emoji;
import harmony.communityservice.board.emoji.domain.Emoji.EmojiId;
import harmony.communityservice.common.annotation.UseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class EmojiQueryService implements LoadEmojiQuery, LoadEmojisQuery {

    private final LoadEmojiPort loadEmojiPort;
    private final LoadEmojisPort loadEmojisPort;

    @Override
    public Emoji load(BoardId boardId, Long emojiType) {
        return loadEmojiPort.loadByBoardIdAndEmojiType(boardId, emojiType);
    }

    @Override
    public Emoji loadById(EmojiId emojiId) {
        return loadEmojiPort.load(emojiId);
    }

    @Override
    public SearchEmojisResponse loadByBoardId(BoardId boardId) {
        List<Emoji> emojis = loadEmojisPort.loadEmojisByBoardId(boardId);
        List<SearchEmojiResponse> searchEmojiResponses = emojis.stream()
                .map(SearchEmojiResponseMapper::convert).toList();
        return new SearchEmojisResponse(searchEmojiResponses);
    }
}
