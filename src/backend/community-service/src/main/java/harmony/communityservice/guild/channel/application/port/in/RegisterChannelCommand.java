package harmony.communityservice.guild.channel.application.port.in;

import harmony.communityservice.common.dto.CommonCommand;
import lombok.Builder;

@Builder(toBuilder = true)
public record RegisterChannelCommand(Long guildId,
                                     String name,
                                     Long userId,
                                     Long categoryId,
                                     String type) implements CommonCommand {
    @Override
    public Long getGuildId() {
        return guildId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }
}
