package harmony.communityservice.community.query.repository.impl;

import harmony.communityservice.community.domain.Comment;
import harmony.communityservice.community.query.repository.CommentQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaCommentQueryRepository;
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
