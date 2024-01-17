package harmony.communityservice.community.command.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.community.command.dto.CommentRegistrationRequestDto;
import harmony.communityservice.community.command.dto.CommentUpdateRequestDto;
import harmony.communityservice.community.command.service.CommentCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/registration/board/comment")
    public BaseResponse<?> registration(@RequestBody CommentRegistrationRequestDto requestDto) {

        commentCommandService.save(requestDto);
        return new BaseResponse<>(HttpStatus.OK.value(),"OK");

    }

    @PatchMapping("/change/comment")
    public BaseResponse<?> update(@RequestBody CommentUpdateRequestDto requestDto) {
        commentCommandService.updateComment(requestDto);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}
