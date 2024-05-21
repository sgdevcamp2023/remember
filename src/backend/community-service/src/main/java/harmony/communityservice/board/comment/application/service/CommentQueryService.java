package harmony.communityservice.board.comment.application.service;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.comment.application.port.in.CountCommentQuery;
import harmony.communityservice.board.comment.application.port.in.LoadCommentsQuery;
import harmony.communityservice.board.comment.application.port.in.LoadCommentsResponse;
import harmony.communityservice.board.comment.application.port.in.LordCommentResponse;
import harmony.communityservice.board.comment.application.port.out.CountCommentPort;
import harmony.communityservice.board.comment.application.port.out.LoadCommentsPort;
import harmony.communityservice.board.comment.domain.Comment;
import harmony.communityservice.common.annotation.UseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class CommentQueryService implements CountCommentQuery, LoadCommentsQuery {

    private final CountCommentPort countCommentPort;
    private final LoadCommentsPort loadCommentsPort;

    @Override
    public Long count(BoardId boardId) {
        return countCommentPort.count(boardId);
    }

    @Override
    public LoadCommentsResponse load(BoardId boardId) {
        List<Comment> comments = loadCommentsPort.loadByBoardId(boardId);
        List<LordCommentResponse> lordCommentResponses = comments.stream()
                .map(LoadCommentResponseMapper::convert).toList();
        return new LoadCommentsResponse(lordCommentResponses);
    }
}
