package harmony.communityservice.community.command.repository;

import harmony.communityservice.community.domain.Comment;

public interface CommentCommandRepository {
    void save(Comment comment);
}
