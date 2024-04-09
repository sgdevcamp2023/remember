package harmony.communityservice.board.emoji.dto;

import jakarta.validation.constraints.NotNull;

public record RegisterEmojiRequest(@NotNull Long boardId,
                                   @NotNull Long emojiType,
                                   @NotNull Long userId) {

}
