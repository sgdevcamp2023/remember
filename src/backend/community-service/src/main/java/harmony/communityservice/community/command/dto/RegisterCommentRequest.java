package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterCommentRequest(@NotNull Long userId,
                                     @NotNull Long boardId,
                                     @NotBlank String comment,
                                     @NotBlank String writerName,
                                     @NotBlank String writerProfile) {

}
