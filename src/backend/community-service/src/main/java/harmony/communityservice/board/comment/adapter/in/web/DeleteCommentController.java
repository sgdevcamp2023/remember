package harmony.communityservice.board.comment.adapter.in.web;

import harmony.communityservice.board.comment.application.port.in.DeleteCommentCommand;
import harmony.communityservice.board.comment.application.port.in.DeleteCommentUseCase;
import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class DeleteCommentController {

    private final DeleteCommentUseCase deleteCommentUseCase;


    @DeleteMapping("/delete/comment")
    public BaseResponse<?> delete(@RequestBody @Validated DeleteCommentRequest deleteCommentRequest) {
        DeleteCommentCommand deleteCommentCommand = new DeleteCommentCommand(deleteCommentRequest.commentId(),
                deleteCommentRequest.userId(),
                deleteCommentRequest.boardId());
        deleteCommentUseCase.delete(deleteCommentCommand);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}
