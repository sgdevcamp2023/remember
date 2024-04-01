package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterCategoryRequest(@NotBlank String name,
                                      @NotNull Long userId,
                                      @NotNull Long guildId) {

}
