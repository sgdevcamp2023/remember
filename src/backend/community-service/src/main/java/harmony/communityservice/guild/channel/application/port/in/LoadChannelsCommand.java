package harmony.communityservice.guild.channel.application.port.in;

import harmony.communityservice.common.dto.CommonCommand;

public record LoadChannelsCommand(Long guildId, Long userId) implements CommonCommand {
    @Override
    public Long getGuildId() {
        return guildId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }
}
