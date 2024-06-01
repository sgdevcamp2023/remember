package harmony.communityservice.guild.channel.adapter.in.web;

import harmony.communityservice.common.dto.CommonRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterChannelRequest(
        @NotNull Long guildId,
        @NotBlank String name,
        @NotNull Long userId,
        Long categoryId,
        @NotBlank String type) implements CommonRequest {

    public RegisterChannelRequest {
        if (categoryId == null) {
            categoryId = 0L;
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
