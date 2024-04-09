package harmony.communityservice.board.comment.service.query.impl;

import harmony.communityservice.board.comment.repository.query.CommentQueryRepository;
import harmony.communityservice.board.comment.service.query.CommentQueryService;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.board.domain.Comment;
import harmony.communityservice.board.comment.mapper.ToSearchCommentResponseMapper;
import harmony.communityservice.board.comment.dto.SearchCommentResponse;
import harmony.communityservice.board.comment.dto.SearchCommentsResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentQueryServiceImpl implements CommentQueryService {

    private final CommentQueryRepository commentQueryRepository;

    @Override
    public Comment searchById(Long commentId) {
        return commentQueryRepository.findCommentById(commentId).orElseThrow(NotFoundDataException::new);
    }

    @Override
    public Long countingByBoardId(Long boardId) {
        return commentQueryRepository.countCommentsByBoardId(boardId);
    }

    @Override
    public SearchCommentsResponse searchListByBoardId(Long boardId) {
        List<SearchCommentResponse> searchCommentResponses = commentQueryRepository.findCommentsByBoardId(boardId)
                .stream()
                .map(c -> ToSearchCommentResponseMapper.convert(c, boardId)).toList();
        return new SearchCommentsResponse(searchCommentResponses);
    }
}
