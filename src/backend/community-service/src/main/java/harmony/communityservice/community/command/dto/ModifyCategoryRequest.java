package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ModifyCategoryRequest {

    @NotNull
    private Long guildId;
    @NotNull
    private Long userId;
    @NotNull
    private Long categoryId;
    @NotBlank
    private String name;
}
