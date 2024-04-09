package harmony.communityservice.guild.channel.dto;

import harmony.communityservice.common.dto.CommonRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterChannelRequest(
        @NotNull Long guildId,
        @NotBlank String name,
        @NotNull Long userId,
        @NotNull Integer categoryId,
        @NotBlank String type) implements CommonRequest {

    public RegisterChannelRequest {
        if (categoryId == null) {
            categoryId = 0;
        }
    }

    @Override
    public Long getGuildId() {
        return guildId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }
}
