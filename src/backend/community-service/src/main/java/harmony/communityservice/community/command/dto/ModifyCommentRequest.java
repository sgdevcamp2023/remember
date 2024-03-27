package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModifyCommentRequest(@NotNull Long userId,
                                   @NotNull Long commentId,
                                   @NotBlank String comment) {
}
