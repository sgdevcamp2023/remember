package harmony.communityservice.guild.category.application.port.in;

import harmony.communityservice.common.dto.CommonCommand;

public record SearchListCommand(Long guildId, Long userId) implements CommonCommand {
    @Override
    public Long getGuildId() {
        return guildId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }
}
