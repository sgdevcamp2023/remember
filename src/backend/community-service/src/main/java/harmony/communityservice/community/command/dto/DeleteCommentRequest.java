package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotNull;

public record DeleteCommentRequest(@NotNull Long commentId, @NotNull Long userId) {

}