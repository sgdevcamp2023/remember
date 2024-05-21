package harmony.communityservice.board.board.adapter.in.web;

import harmony.communityservice.common.dto.VerifyUserRequest;
import jakarta.validation.constraints.NotNull;


public record DeleteBoardRequest(@NotNull Long boardId, @NotNull Long userId) implements VerifyUserRequest {
    @Override
    public Long getUserId() {
        return userId;
    }
}
