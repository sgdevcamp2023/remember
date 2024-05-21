package harmony.communityservice.board.comment.application.service;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.comment.application.port.in.DeleteCommentCommand;
import harmony.communityservice.board.comment.application.port.in.DeleteCommentUseCase;
import harmony.communityservice.board.comment.application.port.in.DeleteCommentsUseCase;
import harmony.communityservice.board.comment.application.port.in.ModifyCommentCommand;
import harmony.communityservice.board.comment.application.port.in.ModifyCommentUseCase;
import harmony.communityservice.board.comment.application.port.in.RegisterCommentCommand;
import harmony.communityservice.board.comment.application.port.in.RegisterCommentUseCase;
import harmony.communityservice.board.comment.application.port.out.DeleteCommentPort;
import harmony.communityservice.board.comment.application.port.out.DeleteCommentsPort;
import harmony.communityservice.board.comment.application.port.out.ModifyCommentPort;
import harmony.communityservice.board.comment.application.port.out.RegisterCommentPort;
import harmony.communityservice.board.comment.domain.Comment;
import harmony.communityservice.board.comment.domain.Comment.CommentId;
import harmony.communityservice.common.annotation.UseCase;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
class CommentCommandService implements RegisterCommentUseCase, ModifyCommentUseCase, DeleteCommentUseCase,
        DeleteCommentsUseCase {

    private final RegisterCommentPort registerCommentPort;
    private final ModifyCommentPort modifyCommentPort;
    private final DeleteCommentPort deleteCommentPort;
    private final DeleteCommentsPort deleteCommentsPort;

    @Override
    public void register(RegisterCommentCommand registerCommentCommand) {
        Comment comment = CommentMapper.convert(registerCommentCommand);
        registerCommentPort.register(comment);
    }

    @Override
    public void modify(ModifyCommentCommand modifyCommentCommand) {
        modifyCommentPort.modify(CommentId.make(modifyCommentCommand.commentId()), modifyCommentCommand.comment(),
                UserId.make(modifyCommentCommand.userId()));
    }

    @Override
    public void delete(DeleteCommentCommand deleteCommentCommand) {
        deleteCommentPort.delete(CommentId.make(deleteCommentCommand.commentId()));
    }

    @Override
    public void deleteByBoardId(BoardId boardId) {
        deleteCommentsPort.deleteByBoardId(boardId);
    }

    @Override
    public void deleteByBoardIds(List<BoardId> boardIds) {
        deleteCommentsPort.deleteByBoardIds(boardIds);
    }
}
