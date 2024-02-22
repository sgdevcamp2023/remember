package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserReadRequestDto {

    @NotNull
    private long userId;

    @NotNull
    private long guildId;

    @NotBlank
    private String profile;

    @NotBlank
    private String nickname;

    @Builder
    public UserReadRequestDto(long userId, long guildId, String profile, String nickname) {
        this.userId = userId;
        this.guildId = guildId;
        this.profile = profile;
        this.nickname = nickname;
    }
}
