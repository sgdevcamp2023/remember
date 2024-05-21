package harmony.communityservice.board.emoji.adapter.in.web;

import harmony.communityservice.board.emoji.application.port.in.DeleteEmojiCommand;
import harmony.communityservice.board.emoji.application.port.in.DeleteEmojiUseCase;
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
public class DeleteEmojiController {

    private final DeleteEmojiUseCase deleteEmojiUseCase;

    @DeleteMapping("/delete/emoji")
    public BaseResponse<?> delete(@RequestBody @Validated DeleteEmojiRequest deleteEmojiRequest) {
        deleteEmojiUseCase.delete(new DeleteEmojiCommand(deleteEmojiRequest.userId(), deleteEmojiRequest.emojiId()));
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}
