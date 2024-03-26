package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ModifyCommentRequest {

    @NotNull
    private Long userId;
    @NotNull
    private Long commentId;
    @NotBlank
    private String comment;
}
