package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DeleteGuildRequest {

    @NotNull
    private Long guildId;

    @NotNull
    private Long managerId;
}
