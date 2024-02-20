package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class GuildRegistrationRequestDto {
    @NotNull
    private Long managerId;
    @NotBlank
    private String name;
}