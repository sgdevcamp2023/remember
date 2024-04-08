package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.Comment;
import harmony.communityservice.community.query.dto.SearchCommentsResponse;
import org.springframework.transaction.annotation.Transactional;

public interface CommentQueryService {

    Comment searchById(Long commentId);

    Long countingByBoardId(Long boardId);

    SearchCommentsResponse searchListByBoardId(Long boardId);
}
