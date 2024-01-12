package harmony.communityservice.community.command.dto;

import lombok.Getter;

@Getter
public class ChannelDeleteRequestDto {

    private Long channelId;
    private Long guildId;
    private Long userId;
}
