package harmony.communityservice.common.event.dto.inner;

import harmony.communityservice.board.board.domain.BoardId;

public record DeleteEmojiEvent(BoardId boardId) {
}
