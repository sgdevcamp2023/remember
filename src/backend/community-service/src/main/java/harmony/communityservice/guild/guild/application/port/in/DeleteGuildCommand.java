package harmony.communityservice.guild.guild.application.port.in;

import harmony.communityservice.common.dto.CommonCommand;

public record DeleteGuildCommand(Long userId, Long guildId) implements CommonCommand {
    @Override
    public Long getGuildId() {
        return guildId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }
}
