package harmony.communityservice.guild.category.application.port.in;

import harmony.communityservice.common.dto.CommonCommand;

public record ModifyCategoryCommand(Long guildId,
                                    Long userId,
                                    Long categoryId,
                                    String name) implements CommonCommand {
    @Override
    public Long getGuildId() {
        return guildId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }
}
