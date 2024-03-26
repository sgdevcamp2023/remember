package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Getter
public class RegisterCommentRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long boardId;
    @NotBlank
    private String comment;
    @NotBlank
    private String writerName;
    @NotBlank
    private String writerProfile;
}
