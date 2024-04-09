package harmony.communityservice.board.comment.service.query.impl;

import harmony.communityservice.board.board.service.query.BoardQueryService;
import harmony.communityservice.board.comment.dto.SearchCommentResponse;
import harmony.communityservice.board.comment.dto.SearchCommentsResponse;
import harmony.communityservice.board.comment.mapper.ToSearchCommentResponseMapper;
import harmony.communityservice.board.comment.service.query.CommentQueryService;
import harmony.communityservice.board.domain.Board;
import harmony.communityservice.board.domain.Comment;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentQueryServiceImpl implements CommentQueryService {

    private final BoardQueryService boardQueryService;

    @Override
    public Long countingByBoardId(Long boardId) {
        return boardQueryService.searchByBoardId(boardId).countingComments();
    }

    @Override
    public SearchCommentsResponse searchListByBoardId(Long boardId) {
        Board targetBoard = boardQueryService.searchByBoardId(boardId);
        List<Comment> comments = targetBoard.getComments();
        List<SearchCommentResponse> searchCommentResponses = comments.stream()
                .map(c -> ToSearchCommentResponseMapper.convert(c, boardId, comments.indexOf(c))).toList();
        return new SearchCommentsResponse(searchCommentResponses);
    }
}
