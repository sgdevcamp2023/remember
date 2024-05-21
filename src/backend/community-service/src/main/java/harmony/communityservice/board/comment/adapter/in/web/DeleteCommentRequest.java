package harmony.communityservice.board.comment.adapter.in.web;

import harmony.communityservice.common.dto.VerifyUserRequest;
import jakarta.validation.constraints.NotNull;

public record DeleteCommentRequest(@NotNull Long commentId, @NotNull Long userId, @NotNull Long boardId) implements
        VerifyUserRequest {

    @Override
    public Long getUserId() {
        return userId;
    }
}