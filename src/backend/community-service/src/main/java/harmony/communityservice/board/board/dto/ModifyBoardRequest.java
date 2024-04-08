package harmony.communityservice.board.board.dto;

import jakarta.validation.constraints.NotNull;

public record ModifyBoardRequest(@NotNull Long userId, @NotNull Long boardId, String title, String content) {
}
