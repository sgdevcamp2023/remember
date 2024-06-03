package harmony.communityservice.user.adapter.in.web;

import harmony.communityservice.common.dto.VerifyUserRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModifyUserNicknameRequest(@NotNull Long userId,
                                        @NotBlank String nickname) implements VerifyUserRequest {
    @Override
    public Long getUserId() {
        return userId;
    }
}
