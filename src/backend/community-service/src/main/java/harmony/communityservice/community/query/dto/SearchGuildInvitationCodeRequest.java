package harmony.communityservice.community.query.dto;

import jakarta.validation.constraints.NotNull;

public record SearchGuildInvitationCodeRequest(@NotNull Long userId,
                                               @NotNull Long guildId) {
}
