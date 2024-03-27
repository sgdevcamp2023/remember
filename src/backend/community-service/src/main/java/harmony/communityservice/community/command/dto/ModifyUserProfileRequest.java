package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModifyUserProfileRequest(@NotNull Long userId,
                                       @NotBlank String profile) {

}
