package harmony.communityservice.common.event.dto.inner;

import harmony.communityservice.board.board.domain.Board.BoardId;
import java.util.List;

public record DeleteCommentsEvent(List<BoardId> boardIds) {
}
