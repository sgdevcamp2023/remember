package harmony.communityservice.guild.channel.dto;

import harmony.communityservice.common.dto.CommonRequest;
import jakarta.validation.constraints.NotNull;

public record DeleteChannelRequest(@NotNull Long channelId, @NotNull Long guildId, @NotNull Long userId,
                                   @NotNull String type)
        implements CommonRequest {

    @Override
    public Long getGuildId() {
        return guildId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }
}
