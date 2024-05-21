package harmony.communityservice.board.emoji.adapter.in.web;

import harmony.communityservice.common.dto.VerifyUserRequest;
import jakarta.validation.constraints.NotNull;

public record DeleteEmojiRequest(@NotNull Long userId, @NotNull Long emojiId) implements VerifyUserRequest {
    @Override
    public Long getUserId() {
        return userId;
    }
}
