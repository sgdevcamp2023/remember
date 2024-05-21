package harmony.communityservice.user.adapter.in.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterUserRequest(@NotNull Long userId,
                                  @NotBlank String email,
                                  @NotBlank String name,
                                  @NotBlank String profile) {

}
