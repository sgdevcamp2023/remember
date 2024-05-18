package harmony.communityservice.board.comment.service.command;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import harmony.communityservice.board.comment.dto.DeleteCommentRequest;
import harmony.communityservice.board.comment.dto.ModifyCommentRequest;
import harmony.communityservice.board.comment.dto.RegisterCommentRequest;
import java.util.List;

public interface CommentCommandService {

    void register(RegisterCommentRequest registerCommentRequest);

    void modify(ModifyCommentRequest modifyCommentRequest);

    void delete(DeleteCommentRequest deleteCommentRequest);

    void deleteListByBoardId(BoardIdJpaVO boardId);

    void deleteListByBoardIds(List<BoardIdJpaVO> boardIds);
}
