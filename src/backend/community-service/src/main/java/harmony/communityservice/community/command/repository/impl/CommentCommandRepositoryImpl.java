package harmony.communityservice.community.command.repository.impl;

import harmony.communityservice.community.command.repository.CommentCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaCommentCommandRepository;
import harmony.communityservice.community.domain.Comment;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentCommandRepositoryImpl implements CommentCommandRepository {

    private final JpaCommentCommandRepository jpaCommentCommandRepository;

    @Override
    public void save(Comment comment) {
        jpaCommentCommandRepository.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        jpaCommentCommandRepository.delete(comment);
    }
}
