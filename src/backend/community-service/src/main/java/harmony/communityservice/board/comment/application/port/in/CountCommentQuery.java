package harmony.communityservice.board.comment.application.port.in;

import harmony.communityservice.board.board.domain.Board.BoardId;

public interface CountCommentQuery {

    Long count(BoardId boardId);
}
