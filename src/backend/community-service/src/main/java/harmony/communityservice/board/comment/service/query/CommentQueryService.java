package harmony.communityservice.board.comment.service.query;

import harmony.communityservice.board.domain.Comment;
import harmony.communityservice.board.comment.dto.SearchCommentsResponse;

public interface CommentQueryService {

    Comment searchById(Long commentId);

    Long countingByBoardId(Long boardId);

    SearchCommentsResponse searchListByBoardId(Long boardId);
}
