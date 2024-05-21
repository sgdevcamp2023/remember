package harmony.communityservice.board.comment.application.port.out;

import harmony.communityservice.board.comment.domain.Comment.CommentId;

public interface DeleteCommentPort {

    void delete(CommentId commentId);
}
