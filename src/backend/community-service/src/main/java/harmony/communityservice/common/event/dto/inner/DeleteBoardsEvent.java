package harmony.communityservice.common.event.dto.inner;

import jakarta.validation.constraints.NotNull;

public record DeleteBoardsEvent(@NotNull Long channelId, @NotNull Long userId) {
}
