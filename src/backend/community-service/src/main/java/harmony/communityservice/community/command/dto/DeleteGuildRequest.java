package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotNull;

public record DeleteGuildRequest(@NotNull Long guildId, @NotNull Long managerId) {
}
