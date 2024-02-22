package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.community.domain.Comment;
import harmony.communityservice.community.query.repository.CommentQueryRepository;
import harmony.communityservice.community.query.service.CommentQueryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentQueryServiceImpl implements CommentQueryService {

    private final CommentQueryRepository commentQueryRepository;

    @Override
    public Comment findById(Long commentId) {
        return commentQueryRepository.findCommentById(commentId).orElseThrow(NotFoundDataException::new);
    }
}
