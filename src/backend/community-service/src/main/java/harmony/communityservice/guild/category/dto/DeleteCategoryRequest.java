package harmony.communityservice.guild.category.dto;

import harmony.communityservice.common.dto.CommonRequest;
import jakarta.validation.constraints.NotNull;

public record DeleteCategoryRequest(@NotNull Long guildId,
                                    @NotNull Integer categoryId,
                                    @NotNull Long userId) implements CommonRequest {
    @Override
    public Long getGuildId() {
        return guildId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }
}
