package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotNull;

public record DeleteCategoryRequest(@NotNull Long guildId,
                                    @NotNull Long categoryId,
                                    @NotNull Long userId) {

}
