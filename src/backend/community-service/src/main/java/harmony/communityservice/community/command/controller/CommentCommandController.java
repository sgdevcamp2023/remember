package harmony.communityservice.community.command.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.community.command.dto.DeleteCommentRequest;
import harmony.communityservice.community.command.dto.RegisterCommentRequest;
import harmony.communityservice.community.command.dto.ModifyCommentRequest;
import harmony.communityservice.community.command.service.CommentCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommentCommandController {

    private final CommentCommandService commentCommandService;

    @PostMapping("/register/board/comment")
    public BaseResponse<?> register(@RequestBody @Validated RegisterCommentRequest registerCommentRequest) {
        commentCommandService.register(registerCommentRequest);
        return new BaseResponse<>(HttpStatus.OK.value(),"OK");
    }

    @PatchMapping("/modify/comment")
    public BaseResponse<?> modify(@RequestBody @Validated ModifyCommentRequest modifyCommentRequest) {
        commentCommandService.modify(modifyCommentRequest);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @DeleteMapping("/delete/comment")
    public BaseResponse<?> delete(@RequestBody @Validated DeleteCommentRequest deleteCommentRequest) {
        commentCommandService.delete(deleteCommentRequest);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}
