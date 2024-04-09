package harmony.communityservice.board.comment.service.command.impl;

import harmony.communityservice.board.comment.dto.DeleteCommentRequest;
import harmony.communityservice.board.comment.dto.ModifyCommentRequest;
import harmony.communityservice.board.comment.dto.RegisterCommentRequest;
import harmony.communityservice.board.comment.mapper.ToCommentMapper;
import harmony.communityservice.board.comment.repository.command.CommentCommandRepository;
import harmony.communityservice.board.comment.service.command.CommentCommandService;
import harmony.communityservice.board.comment.service.query.CommentQueryService;
import harmony.communityservice.board.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class CommentCommandServiceImpl implements CommentCommandService {

    private final CommentCommandRepository commentCommandRepository;
    private final CommentQueryService commentQueryService;

    @Override
    public void register(RegisterCommentRequest registerCommentRequest) {
        Comment comment = createComment(registerCommentRequest);
        commentCommandRepository.save(comment);
    }

    private Comment createComment(RegisterCommentRequest registerCommentRequest) {
        return ToCommentMapper.convert(registerCommentRequest);
    }

    @Override
    public void modify(ModifyCommentRequest modifyCommentRequest) {
        Comment targetComment = commentQueryService.searchById(modifyCommentRequest.commentId());
        targetComment.modify(modifyCommentRequest);
    }

    @Override
    public void delete(DeleteCommentRequest deleteCommentRequest) {
        Comment targetComment = commentQueryService.searchById(deleteCommentRequest.commentId());
        targetComment.verifyWriter(deleteCommentRequest.userId());
        commentCommandRepository.delete(targetComment);
    }
}
