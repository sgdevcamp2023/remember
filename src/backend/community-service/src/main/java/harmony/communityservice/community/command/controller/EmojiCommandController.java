package harmony.communityservice.community.command.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.community.command.dto.DeleteEmojiRequest;
import harmony.communityservice.community.command.dto.RegisterEmojiRequest;
import harmony.communityservice.community.command.service.EmojiCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class EmojiCommandController {

    private final EmojiCommandService emojiCommandService;

    @PostMapping("/register/board/emoji")
    public BaseResponse<?> register(@RequestBody @Validated RegisterEmojiRequest registerEmojiRequest) {
        emojiCommandService.register(registerEmojiRequest);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @DeleteMapping("/delete/emoji")
    public BaseResponse<?> delete(@RequestBody @Validated DeleteEmojiRequest deleteEmojiRequest) {
        emojiCommandService.delete(deleteEmojiRequest);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}
