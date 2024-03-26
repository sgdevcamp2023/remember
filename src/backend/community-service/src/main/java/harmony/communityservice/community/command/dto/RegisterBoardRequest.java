package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RegisterBoardRequest {

    @NotNull
    private Long userId;
    @NotNull
    private Long channelId;
    @NotNull
    private Long guildId;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String writerProfile;
}
