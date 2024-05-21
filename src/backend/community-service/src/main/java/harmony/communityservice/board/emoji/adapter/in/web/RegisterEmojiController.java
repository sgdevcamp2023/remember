package harmony.communityservice.board.emoji.adapter.in.web;

import harmony.communityservice.board.emoji.application.port.in.RegisterEmojiCommand;
import harmony.communityservice.board.emoji.application.port.in.RegisterEmojiUseCase;
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
public class RegisterEmojiController {

    private final RegisterEmojiUseCase registerEmojiUseCase;

    @PostMapping("/register/board/emoji")
    public BaseResponse<?> register(@RequestBody @Validated RegisterEmojiRequest registerEmojiRequest) {
        registerEmojiUseCase.register(new RegisterEmojiCommand(registerEmojiRequest.boardId(),
                registerEmojiRequest.emojiType(), registerEmojiRequest.userId()));
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}
