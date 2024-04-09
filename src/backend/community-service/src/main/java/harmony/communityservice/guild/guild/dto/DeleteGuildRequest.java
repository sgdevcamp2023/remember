package harmony.communityservice.guild.guild.dto;

import harmony.communityservice.common.dto.ManagerRequest;
import jakarta.validation.constraints.NotNull;

public record DeleteGuildRequest(@NotNull Long guildId, @NotNull Long managerId) implements ManagerRequest {
    @Override
    public Long getGuildId() {
        return guildId;
    }

    @Override
    public Long getManagerId() {
        return managerId;
    }
}
