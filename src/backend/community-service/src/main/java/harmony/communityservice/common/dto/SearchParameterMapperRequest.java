package harmony.communityservice.common.dto;

import harmony.communityservice.common.dto.CommonRequest;
import jakarta.validation.constraints.NotNull;

public record SearchParameterMapperRequest(@NotNull Long guildId, @NotNull Long userId) implements CommonRequest {
    @Override
    public Long getGuildId() {
        return guildId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }
}
