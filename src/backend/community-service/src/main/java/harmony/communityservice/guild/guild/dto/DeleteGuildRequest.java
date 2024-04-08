package harmony.communityservice.guild.guild.dto;

import jakarta.validation.constraints.NotNull;

public record DeleteGuildRequest(@NotNull Long guildId, @NotNull Long managerId) {
}
