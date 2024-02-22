package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserProfileUpdateRequestDto {
    @NotNull
    private Long userId;
    @NotBlank
    private String profile;
}
