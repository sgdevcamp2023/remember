package harmony.communityservice.board.board.adapter.in.web;

import harmony.communityservice.common.dto.CommonRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterBoardRequest(@NotNull Long userId,
                                   @NotNull Long channelId,
                                   @NotNull Long guildId,
                                   @NotBlank String title,
                                   @NotBlank String content) implements CommonRequest {

    @Override
    public Long getGuildId() {
        return guildId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }
}
