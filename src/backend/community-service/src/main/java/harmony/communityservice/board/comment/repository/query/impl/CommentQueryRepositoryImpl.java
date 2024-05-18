package harmony.communityservice.board.comment.repository.query.impl;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import harmony.communityservice.board.comment.domain.Comment;
import harmony.communityservice.board.comment.repository.query.CommentQueryRepository;
import harmony.communityservice.board.comment.repository.query.jpa.JpaCommentQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentQueryRepositoryImpl implements CommentQueryRepository {

    private final JpaCommentQueryRepository jpaCommentQueryRepository;

    @Override
    public Long countListByBoardId(BoardIdJpaVO boardId) {
        return jpaCommentQueryRepository.countCommentsByBoardId(boardId);
    }

    @Override
    public List<Comment> findListByBoardId(BoardIdJpaVO boardId) {
        return jpaCommentQueryRepository.findCommentsByBoardId(boardId);
    }
}
