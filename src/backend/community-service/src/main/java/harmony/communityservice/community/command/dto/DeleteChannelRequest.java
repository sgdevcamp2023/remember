package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotNull;

public record DeleteChannelRequest(@NotNull Long channelId, @NotNull Long guildId, @NotNull Long userId) {

}
