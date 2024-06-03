package harmony.communityservice.guild.guild.adapter.in.web;

import harmony.communityservice.common.dto.CommonRequest;
import jakarta.validation.constraints.NotNull;

public record LoadGuildInvitationCodeRequest(@NotNull Long userId,
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
