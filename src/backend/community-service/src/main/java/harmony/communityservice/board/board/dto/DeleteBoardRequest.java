package harmony.communityservice.board.board.dto;

import jakarta.validation.constraints.NotNull;


public record DeleteBoardRequest(@NotNull Long boardId, @NotNull Long userId) {
}
