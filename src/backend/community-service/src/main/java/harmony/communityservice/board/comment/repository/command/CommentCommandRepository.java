package harmony.communityservice.board.comment.repository.command;

import harmony.communityservice.board.domain.Comment;

public interface CommentCommandRepository {
    void save(Comment comment);

    void delete(Comment comment);
}
