package harmony.communityservice.board.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModifyCommentRequest(@NotNull Long userId,
                                   @NotNull Long commentId,
                                   @NotBlank String comment) {
}
