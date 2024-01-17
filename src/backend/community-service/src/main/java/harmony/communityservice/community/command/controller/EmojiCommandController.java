package harmony.communityservice.community.command.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.community.command.dto.EmojiRegistrationRequestDto;
import harmony.communityservice.community.command.service.EmojiCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class EmojiCommandController {

    private final EmojiCommandService emojiCommandService;

    @PostMapping("/board/emoji")
    public BaseResponse<?> registration(@RequestBody @Validated EmojiRegistrationRequestDto requestDto) {
        emojiCommandService.save(requestDto);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}
