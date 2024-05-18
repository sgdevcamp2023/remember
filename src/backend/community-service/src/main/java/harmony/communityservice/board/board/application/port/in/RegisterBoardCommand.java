package harmony.communityservice.board.board.application.port.in;

import harmony.communityservice.common.dto.CommonCommand;
import lombok.Builder;

@Builder(toBuilder = true)
public record RegisterBoardCommand(Long userId,
                                   Long channelId,
                                   Long guildId,
                                   String title,
                                   String content) implements CommonCommand {
    @Override
    public Long getGuildId() {
        return guildId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }
}
