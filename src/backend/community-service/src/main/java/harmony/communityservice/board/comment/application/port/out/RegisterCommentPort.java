package harmony.communityservice.board.comment.application.port.out;

import harmony.communityservice.board.comment.domain.Comment;

public interface RegisterCommentPort {
    void register(Comment comment);
}
