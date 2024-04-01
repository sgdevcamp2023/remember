package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModifyCategoryRequest(@NotNull Long guildId,
                                    @NotNull Long userId,
                                    @NotNull Long categoryId,
                                    @NotBlank String name) {

}
