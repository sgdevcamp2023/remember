package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentDeleteRequestDto {
    @NotNull
    private Long commentId;
    @NotNull
    private Long userId;
}
