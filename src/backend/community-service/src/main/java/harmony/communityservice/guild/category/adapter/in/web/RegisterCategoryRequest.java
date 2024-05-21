package harmony.communityservice.guild.category.adapter.in.web;

import harmony.communityservice.common.dto.CommonRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterCategoryRequest(@NotBlank String name,
                                      @NotNull Long userId,
                                      @NotNull Long guildId) implements CommonRequest {

    @Override
    public Long getGuildId() {
        return guildId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }
}
