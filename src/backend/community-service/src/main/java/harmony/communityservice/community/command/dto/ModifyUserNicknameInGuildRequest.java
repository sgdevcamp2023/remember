package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public record ModifyUserNicknameInGuildRequest(@NotNull Long userId,
                                               @NotNull Long guildId,
                                               @NotBlank String nickname) {

}
