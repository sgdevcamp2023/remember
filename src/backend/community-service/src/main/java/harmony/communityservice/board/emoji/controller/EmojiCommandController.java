package harmony.communityservice.board.emoji.controller;

import harmony.communityservice.board.emoji.dto.DeleteEmojiRequest;
import harmony.communityservice.board.emoji.dto.RegisterEmojiRequest;
import harmony.communityservice.board.emoji.service.command.EmojiCommandService;
import harmony.communityservice.common.dto.BaseResponse;
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
