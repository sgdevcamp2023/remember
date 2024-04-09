package harmony.communityservice.board.comment.dto;

import jakarta.validation.constraints.NotNull;

public record DeleteCommentRequest(@NotNull Integer commentId, @NotNull Long userId, @NotNull Long boardId) {

}