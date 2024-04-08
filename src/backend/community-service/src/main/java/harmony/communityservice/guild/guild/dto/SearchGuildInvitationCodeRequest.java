package harmony.communityservice.guild.guild.dto;

import harmony.communityservice.common.dto.CommonRequest;
import jakarta.validation.constraints.NotNull;

public record SearchGuildInvitationCodeRequest(@NotNull Long userId,
                                               @NotNull Long guildId) implements CommonRequest {
    @Override
    public Long getGuildId() {
        return guildId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }
}
