package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterChannelRequest {

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
