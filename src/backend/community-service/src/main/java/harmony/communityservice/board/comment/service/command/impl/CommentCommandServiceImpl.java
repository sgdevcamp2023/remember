package harmony.communityservice.board.comment.service.command.impl;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import harmony.communityservice.board.comment.domain.Comment;
import harmony.communityservice.board.comment.domain.CommentId;
import harmony.communityservice.board.comment.dto.DeleteCommentRequest;
import harmony.communityservice.board.comment.dto.ModifyCommentRequest;
import harmony.communityservice.board.comment.dto.RegisterCommentRequest;
import harmony.communityservice.board.comment.mapper.ToCommentMapper;
import harmony.communityservice.board.comment.repository.command.CommentCommandRepository;
import harmony.communityservice.board.comment.service.command.CommentCommandService;
import harmony.communityservice.common.exception.NotFoundDataException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class CommentCommandServiceImpl implements CommentCommandService {

    private final CommentCommandRepository commentCommandRepository;

    @Override
    public void register(RegisterCommentRequest registerCommentRequest) {
        Comment comment = ToCommentMapper.convert(registerCommentRequest);
        commentCommandRepository.save(comment);
    }

    @Override
    public void modify(ModifyCommentRequest modifyCommentRequest) {
        Comment targetComment = commentCommandRepository.findById(CommentId.make(modifyCommentRequest.commentId()))
                .orElseThrow(NotFoundDataException::new);
        targetComment.modify(modifyCommentRequest.userId(), modifyCommentRequest.comment());
    }

    @Override
    public void delete(DeleteCommentRequest deleteCommentRequest) {
        commentCommandRepository.deleteById(CommentId.make(deleteCommentRequest.commentId()));
    }

    @Override
    public void deleteListByBoardId(BoardIdJpaVO boardId) {
        commentCommandRepository.deleteListByBoardId(boardId);
    }

    @Override
    public void deleteListByBoardIds(List<BoardIdJpaVO> boardIds) {
        commentCommandRepository.deleteListByBoardIds(boardIds);
    }
}
