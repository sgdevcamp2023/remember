package harmony.communityservice.guild.category.dto;

import harmony.communityservice.common.dto.CommonRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModifyCategoryRequest(@NotNull Long guildId,
                                    @NotNull Long userId,
                                    @NotNull Long categoryId,
                                    @NotBlank String name) implements CommonRequest {

    @Override
    public Long getGuildId() {
        return guildId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }
}
