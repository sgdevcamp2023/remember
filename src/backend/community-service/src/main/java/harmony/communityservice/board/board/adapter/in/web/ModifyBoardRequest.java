package harmony.communityservice.board.board.adapter.in.web;

import harmony.communityservice.common.dto.VerifyUserRequest;
import jakarta.validation.constraints.NotNull;

public record ModifyBoardRequest(@NotNull Long userId, @NotNull Long boardId, String title, String content) implements
        VerifyUserRequest {
    @Override
    public Long getUserId() {
        return userId;
    }
}
