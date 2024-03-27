package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterChannelRequest(
        @NotNull Long guildId,
        @NotBlank String name,
        @NotNull Long userId,
        @NotNull Long categoryId,
        @NotBlank String type) {

    public RegisterChannelRequest {
        if (categoryId == null) {
            categoryId = 0L;
        }
    }
}
