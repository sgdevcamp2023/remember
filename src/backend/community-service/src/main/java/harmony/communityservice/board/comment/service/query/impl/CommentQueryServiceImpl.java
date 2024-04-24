package harmony.communityservice.board.comment.service.query.impl;

import harmony.communityservice.board.comment.dto.SearchCommentResponse;
import harmony.communityservice.board.comment.dto.SearchCommentsResponse;
import harmony.communityservice.board.comment.mapper.ToSearchCommentResponseMapper;
import harmony.communityservice.board.comment.repository.query.CommentQueryRepository;
import harmony.communityservice.board.comment.service.query.CommentQueryService;
import harmony.communityservice.board.comment.domain.Comment;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentQueryServiceImpl implements CommentQueryService {

    private final CommentQueryRepository commentQueryRepository;

    @Override
    public Long countingByBoardId(Long boardId) {
        return commentQueryRepository.countListByBoardId(boardId);
    }

    @Override
    public SearchCommentsResponse searchListByBoardId(Long boardId) {
        List<Comment> comments = commentQueryRepository.findListByBoardId(boardId);
        List<SearchCommentResponse> searchCommentResponses = comments.stream()
                .map(c -> ToSearchCommentResponseMapper.convert(c, boardId, comments.indexOf(c))).toList();
        return new SearchCommentsResponse(searchCommentResponses);
    }
}
