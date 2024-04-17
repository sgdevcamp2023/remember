package harmony.communityservice.board.emoji.dto;

import harmony.communityservice.common.dto.VerifyUserRequest;
import jakarta.validation.constraints.NotNull;

public record RegisterEmojiRequest(@NotNull Long boardId,
                                   @NotNull Long emojiType,
                                   @NotNull Long userId) implements VerifyUserRequest {

    @Override
    public Long getUserId() {
        return userId;
    }
}
