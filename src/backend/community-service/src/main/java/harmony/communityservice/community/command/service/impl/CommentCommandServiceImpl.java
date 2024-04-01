package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.community.command.dto.DeleteCommentRequest;
import harmony.communityservice.community.command.dto.RegisterCommentRequest;
import harmony.communityservice.community.command.dto.ModifyCommentRequest;
import harmony.communityservice.community.command.repository.CommentCommandRepository;
import harmony.communityservice.community.command.service.CommentCommandService;
import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.domain.Comment;
import harmony.communityservice.community.mapper.ToCommentMapper;
import harmony.communityservice.community.query.service.BoardQueryService;
import harmony.communityservice.community.query.service.CommentQueryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentCommandServiceImpl implements CommentCommandService {

    private final CommentCommandRepository commentCommandRepository;
    private final BoardQueryService boardQueryService;
    private final CommentQueryService commentQueryService;

    @Override
    public void register(RegisterCommentRequest registerCommentRequest) {
        Comment comment = createComment(registerCommentRequest);
        commentCommandRepository.save(comment);
    }

    private Comment createComment(RegisterCommentRequest registerCommentRequest) {
        Board targetBoard = boardQueryService.searchByBoardId(registerCommentRequest.boardId());
        return ToCommentMapper.convert(registerCommentRequest, targetBoard);
    }

    @Override
    public void modify(ModifyCommentRequest modifyCommentRequest) {
        Comment targetComment = commentQueryService.searchById(modifyCommentRequest.commentId());
        targetComment.verifyWriter(modifyCommentRequest.userId());
        targetComment.modify(modifyCommentRequest.comment());
    }

    @Override
    public void delete(DeleteCommentRequest deleteCommentRequest) {
        Comment targetComment = commentQueryService.searchById(deleteCommentRequest.commentId());
        targetComment.verifyWriter(deleteCommentRequest.userId());
        commentCommandRepository.delete(targetComment);
    }
}
