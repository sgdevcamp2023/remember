package harmony.communityservice.board.comment.service.query;

import harmony.communityservice.board.comment.dto.SearchCommentsResponse;

public interface CommentQueryService {

    Long countingByBoardId(Long boardId);

    SearchCommentsResponse searchListByBoardId(Long boardId);
}
