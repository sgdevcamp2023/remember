package harmony.communityservice.board.comment.repository.command.impl;

import harmony.communityservice.board.comment.repository.command.CommentCommandRepository;
import harmony.communityservice.board.comment.repository.command.jpa.JpaCommentCommandRepository;
import harmony.communityservice.board.comment.domain.Comment;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentCommandRepositoryImpl implements CommentCommandRepository {

    private final JpaCommentCommandRepository jpaCommentCommandRepository;


    @Override
    public void save(Comment comment) {
        jpaCommentCommandRepository.save(comment);
    }

    @Override
    public void deleteById(Long commentId) {
        jpaCommentCommandRepository.deleteById(commentId);
    }

    @Override
    public Optional<Comment> findById(Long commentId) {
        return jpaCommentCommandRepository.findById(commentId);
    }

    @Override
    public void deleteListByBoardId(Long boardId) {
        jpaCommentCommandRepository.deleteCommentsByBoardId(boardId);
    }
}
