package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class GuildUpdateNicknameRequestDto {

    @NotNull
    private Long userId;
    @NotNull
    private Long guildId;
    @NotBlank
    private String nickname;
}