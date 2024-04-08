package harmony.communityservice.board.comment.repository.command.impl;

import harmony.communityservice.board.comment.repository.command.CommentCommandRepository;
import harmony.communityservice.board.comment.repository.command.jpa.JpaCommentCommandRepository;
import harmony.communityservice.board.domain.Comment;
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
