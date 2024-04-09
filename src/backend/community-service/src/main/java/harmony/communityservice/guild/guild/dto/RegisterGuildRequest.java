package harmony.communityservice.guild.guild.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterGuildRequest(@NotNull Long managerId,
                                   @NotBlank String name) {

}
