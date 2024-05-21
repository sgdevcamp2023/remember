package harmony.communityservice.board.emoji.application.port.out;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.emoji.domain.Emoji;
import java.util.List;

public interface LoadEmojisPort {

    List<Emoji> loadEmojisByBoardId(BoardId boardId);
}
