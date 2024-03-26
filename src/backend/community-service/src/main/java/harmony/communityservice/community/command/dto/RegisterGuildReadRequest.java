package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RegisterGuildReadRequest {

    @NotNull
    private Long guildId;
    @NotNull
    private Long userId;
    @NotBlank
    private String name;
    @NotBlank
    private String profile;

    @Builder
    public RegisterGuildReadRequest(Long guildId, Long userId, String name, String profile) {
        this.guildId = guildId;
        this.userId = userId;
        this.name = name;
        this.profile = profile;
    }
}
