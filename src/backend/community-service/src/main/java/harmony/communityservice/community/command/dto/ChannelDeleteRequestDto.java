package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChannelDeleteRequestDto {

    @NotNull
    private Long channelId;
    @NotNull
    private Long guildId;
    @NotNull
    private Long userId;
}
