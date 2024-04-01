package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotNull;

public record DeleteEmojiRequest(@NotNull Long userId, @NotNull Long emojiId) {
}
