package harmony.communityservice.board.emoji.application.port.out;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.user.domain.User.UserId;

public interface RegisterEmojiPort {

    void register(BoardId boardId, UserId userId, Long emojiType);
}
