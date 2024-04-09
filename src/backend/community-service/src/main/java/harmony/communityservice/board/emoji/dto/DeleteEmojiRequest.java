package harmony.communityservice.board.emoji.dto;

import jakarta.validation.constraints.NotNull;

public record DeleteEmojiRequest(@NotNull Long userId, @NotNull Long emojiId) {
}
