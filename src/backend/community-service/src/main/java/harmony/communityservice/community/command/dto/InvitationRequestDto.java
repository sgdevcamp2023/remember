package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class InvitationRequestDto {

    @NotNull
    private Long userId;
    @NotNull
    private Long guildId;
}
