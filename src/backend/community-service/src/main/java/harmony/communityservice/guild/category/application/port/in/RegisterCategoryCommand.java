package harmony.communityservice.guild.category.application.port.in;

import harmony.communityservice.common.dto.CommonCommand;

public record RegisterCategoryCommand(String name,
                                      Long userId,
                                      Long guildId) implements CommonCommand {
    @Override
    public Long getGuildId() {
        return guildId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }
}
