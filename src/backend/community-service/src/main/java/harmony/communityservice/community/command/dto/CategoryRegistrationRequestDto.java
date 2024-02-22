package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CategoryRegistrationRequestDto {
    @NotBlank
    private String name;
    @NotNull
    private Long userId;
    @NotNull
    private Long guildId;
}
