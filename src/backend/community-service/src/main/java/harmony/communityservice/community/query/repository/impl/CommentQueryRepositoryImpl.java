package harmony.communityservice.community.query.repository.impl;

import harmony.communityservice.community.domain.Comment;
import harmony.communityservice.community.query.repository.CommentQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaCommentQueryRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentQueryRepositoryImpl implements CommentQueryRepository {

    private final JpaCommentQueryRepository jpaCommentQueryRepository;

    @Override

    public Optional<Comment> findCommentById(Long commentId) {
        return jpaCommentQueryRepository.findById(commentId);
    }
}
