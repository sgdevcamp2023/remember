package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.community.domain.Comment;
import harmony.communityservice.community.mapper.ToSearchCommentResponseMapper;
import harmony.communityservice.community.query.dto.SearchCommentResponse;
import harmony.communityservice.community.query.dto.SearchCommentsResponse;
import harmony.communityservice.community.query.repository.CommentQueryRepository;
import harmony.communityservice.community.query.service.CommentQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
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
