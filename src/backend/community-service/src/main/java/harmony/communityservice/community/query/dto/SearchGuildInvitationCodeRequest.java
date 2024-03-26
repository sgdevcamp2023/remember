package harmony.communityservice.community.query.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SearchGuildInvitationCodeRequest {

    @NotNull
    private Long userId;
    @NotNull
    private Long guildId;
}
