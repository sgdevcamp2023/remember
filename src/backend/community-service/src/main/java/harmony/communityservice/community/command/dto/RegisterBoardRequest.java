package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterBoardRequest(@NotNull Long userId,
                                   @NotNull Long channelId,
                                   @NotNull Long guildId,
                                   @NotBlank String title,
                                   @NotBlank String content,
                                   @NotBlank String writerProfile) {

}
