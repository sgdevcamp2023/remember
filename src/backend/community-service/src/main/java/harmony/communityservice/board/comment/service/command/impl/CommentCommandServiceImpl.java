package harmony.communityservice.board.comment.service.command.impl;

import harmony.communityservice.board.board.service.query.BoardQueryService;
import harmony.communityservice.board.comment.dto.DeleteCommentRequest;
import harmony.communityservice.board.comment.dto.ModifyCommentRequest;
import harmony.communityservice.board.comment.dto.RegisterCommentRequest;
import harmony.communityservice.board.comment.mapper.ToCommentMapper;
import harmony.communityservice.board.comment.service.command.CommentCommandService;
import harmony.communityservice.board.domain.Board;
import harmony.communityservice.board.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class CommentCommandServiceImpl implements CommentCommandService {

    private final BoardQueryService boardQueryService;

    @Override
    public void register(RegisterCommentRequest registerCommentRequest) {
        Board targetBoard = boardQueryService.searchByBoardId(registerCommentRequest.boardId());
        Comment comment = ToCommentMapper.convert(registerCommentRequest);
        targetBoard.registerComment(comment);
    }

    @Override
    public void modify(ModifyCommentRequest modifyCommentRequest) {
        Board targetBoard = boardQueryService.searchByBoardId(modifyCommentRequest.boardId());
        targetBoard.modifyComment(modifyCommentRequest);
    }

    @Override
    public void delete(DeleteCommentRequest deleteCommentRequest) {
        Board targetBoard = boardQueryService.searchByBoardId(deleteCommentRequest.boardId());
        targetBoard.deleteComment(deleteCommentRequest.commentId(), deleteCommentRequest.userId());
    }
}
