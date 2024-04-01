package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotNull;


public record DeleteBoardRequest(@NotNull Long boardId, @NotNull Long userId) {
}
