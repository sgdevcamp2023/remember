package harmony.communityservice.board.comment.application.port.in;

import harmony.communityservice.board.board.domain.Board.BoardId;

public interface LoadCommentsQuery {

    LoadCommentsResponse load(BoardId boardId);
}
