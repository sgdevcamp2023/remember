package harmony.communityservice.board.comment.adapter.in.web;

import harmony.communityservice.board.comment.application.port.in.ModifyCommentCommand;
import harmony.communityservice.board.comment.application.port.in.ModifyCommentUseCase;
import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class ModifyCommentController {

    private final ModifyCommentUseCase modifyCommentUseCase;

    @PatchMapping("/modify/comment")
    public BaseResponse<?> modify(@RequestBody @Validated ModifyCommentRequest modifyCommentRequest) {
        ModifyCommentCommand modifyCommentCommand = getModifyCommentCommand(modifyCommentRequest);
        modifyCommentUseCase.modify(modifyCommentCommand);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    private ModifyCommentCommand getModifyCommentCommand(ModifyCommentRequest modifyCommentRequest) {
        return ModifyCommentCommand.builder()
                .userId(modifyCommentRequest.userId())
                .boardId(modifyCommentRequest.boardId())
                .commentId(modifyCommentRequest.commentId())
                .comment(modifyCommentRequest.comment())
                .build();
    }

}
