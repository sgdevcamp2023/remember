package harmony.communityservice.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModifyUserNicknameRequest(@NotNull Long userId,
                                        @NotBlank String nickname) {
}
