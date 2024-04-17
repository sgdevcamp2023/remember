package harmony.communityservice.board.comment.dto;

import harmony.communityservice.common.dto.VerifyUserRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterCommentRequest(@NotNull Long userId,
                                     @NotNull Long boardId,
                                     @NotBlank String comment,
                                     @NotBlank String writerName,
                                     @NotBlank String writerProfile) implements VerifyUserRequest {

    @Override
    public Long getUserId() {
        return userId;
    }
}
