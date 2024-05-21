package harmony.communityservice.board.comment.adapter.in.web;

import harmony.communityservice.board.comment.application.port.in.RegisterCommentCommand;
import harmony.communityservice.board.comment.application.port.in.RegisterCommentUseCase;
import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class RegisterCommentController {

    private final RegisterCommentUseCase registerCommentUseCase;

    @PostMapping("/register/board/comment")
    public BaseResponse<?> register(@RequestBody @Validated RegisterCommentRequest registerCommentRequest) {
        RegisterCommentCommand registerCommentCommand = getRegisterCommentCommand(registerCommentRequest);
        registerCommentUseCase.register(registerCommentCommand);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    private RegisterCommentCommand getRegisterCommentCommand(RegisterCommentRequest registerCommentRequest) {
        return RegisterCommentCommand.builder()
                .boardId(registerCommentRequest.boardId())
                .userId(registerCommentRequest.userId())
                .writerName(registerCommentRequest.writerName())
                .comment(registerCommentRequest.comment())
                .writerProfile(registerCommentRequest.writerProfile())
                .build();
    }
}
