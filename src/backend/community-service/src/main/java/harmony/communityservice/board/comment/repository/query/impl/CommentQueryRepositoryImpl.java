package harmony.communityservice.board.comment.repository.query.impl;

import harmony.communityservice.board.comment.repository.query.CommentQueryRepository;
import harmony.communityservice.board.comment.repository.query.jpa.JpaCommentQueryRepository;
import harmony.communityservice.board.domain.Comment;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentQueryRepositoryImpl implements CommentQueryRepository {

    private final JpaCommentQueryRepository jpaCommentQueryRepository;

    @Override
    public Optional<Comment> findCommentById(Long commentId) {
        return jpaCommentQueryRepository.findById(commentId);
    }

    @Override
    public Long countCommentsByBoardId(Long boardId) {
        return jpaCommentQueryRepository.countCommentsByBoardId(boardId);
    }

    @Override
    public List<Comment> findCommentsByBoardId(Long boardId) {
        return jpaCommentQueryRepository.findCommentsByBoardId(boardId);
    }
}
