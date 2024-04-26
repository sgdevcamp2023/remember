package harmony.communityservice.board.comment.service.command;

import harmony.communityservice.board.board.domain.BoardId;
import harmony.communityservice.board.comment.dto.DeleteCommentRequest;
import harmony.communityservice.board.comment.dto.ModifyCommentRequest;
import harmony.communityservice.board.comment.dto.RegisterCommentRequest;
import java.util.List;

public interface CommentCommandService {

    void register(RegisterCommentRequest registerCommentRequest);

    void modify(ModifyCommentRequest modifyCommentRequest);

    void delete(DeleteCommentRequest deleteCommentRequest);

    void deleteListByBoardId(BoardId boardId);

    void deleteListByBoardIds(List<BoardId> boardIds);
}
