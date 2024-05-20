package harmony.communityservice.board.comment.application.port.out;

import harmony.communityservice.board.board.domain.Board.BoardId;

public interface CountCommentPort {

    Long count(BoardId boardId);
}
