package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder.Default;
import lombok.Data;
import lombok.Getter;

@Data
public class ChannelRegistrationRequestDto {

    @NotNull
    private Long guildId;
    @NotBlank
    private String name;
    @NotNull
    private Long userId;
    @NotNull
    private long categoryId = 0L;
    @NotBlank
    private String type;
}
