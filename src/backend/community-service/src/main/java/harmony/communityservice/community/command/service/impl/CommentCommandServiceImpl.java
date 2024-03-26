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

        Board findBoard = boardQueryService.searchByBoardId(registerCommentRequest.getBoardId());
        Comment comment = ToCommentMapper.convert(registerCommentRequest, findBoard);
        commentCommandRepository.save(comment);
    }

    @Override
    public void modify(ModifyCommentRequest modifyCommentRequest) {
        Comment findComment = commentQueryService.searchById(modifyCommentRequest.getCommentId());
        findComment.verifyWriter(modifyCommentRequest.getUserId());
        findComment.modify(modifyCommentRequest.getComment());
    }

    @Override
    public void delete(DeleteCommentRequest deleteCommentRequest) {
        Comment findComment = commentQueryService.searchById(deleteCommentRequest.getCommentId());
        findComment.verifyWriter(deleteCommentRequest.getUserId());
        commentCommandRepository.delete(findComment);
    }
}
