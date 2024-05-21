package harmony.communityservice.guild.channel.application.port.in;

import harmony.communityservice.common.dto.CommonCommand;
import lombok.Builder;

@Builder(toBuilder = true)
public record DeleteChannelCommand(Long channelId, Long guildId, Long userId,
                                   String type)
        implements CommonCommand {
    @Override
    public Long getGuildId() {
        return guildId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }
}
