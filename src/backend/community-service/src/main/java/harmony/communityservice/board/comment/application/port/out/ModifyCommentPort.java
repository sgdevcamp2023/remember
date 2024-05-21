package harmony.communityservice.board.comment.application.port.out;

import harmony.communityservice.board.comment.domain.Comment.CommentId;
import harmony.communityservice.user.domain.User.UserId;

public interface ModifyCommentPort {

    void modify(CommentId commentId, String comment, UserId userId);
}
