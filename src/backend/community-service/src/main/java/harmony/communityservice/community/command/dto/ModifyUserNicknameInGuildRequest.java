package harmony.communityservice.community.command.dto;

import harmony.communityservice.common.dto.CommonRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public record ModifyUserNicknameInGuildRequest(@NotNull Long userId,
                                               @NotNull Long guildId,
                                               @NotBlank String nickname) implements CommonRequest {

    @Override
    public Long getGuildId() {
        return guildId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }
}
