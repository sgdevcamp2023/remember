package harmony.communityservice.guild.guild.adapter.in.web;

import harmony.communityservice.common.dto.VerifyUserRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterGuildRequest(@NotNull Long managerId,
                                   @NotBlank String name) implements VerifyUserRequest {

    @Override
    public Long getUserId() {
        return managerId;
    }
}
