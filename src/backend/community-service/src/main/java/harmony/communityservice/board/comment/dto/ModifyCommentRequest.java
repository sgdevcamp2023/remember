package harmony.communityservice.board.comment.dto;

import harmony.communityservice.common.dto.VerifyUserRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModifyCommentRequest(@NotNull Long userId,
                                   @NotNull Long boardId,
                                   @NotNull Long commentId,
                                   @NotBlank String comment) implements VerifyUserRequest {
    @Override
    public Long getUserId() {
        return userId;
    }
}
