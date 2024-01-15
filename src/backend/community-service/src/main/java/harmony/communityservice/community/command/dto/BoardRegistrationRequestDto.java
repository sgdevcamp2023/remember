package harmony.communityservice.community.command.dto;

import lombok.Getter;

@Getter
public class BoardRegistrationRequestDto {

    private Long userId;
    private Long channelId;
    private Long guildId;
    private String title;
    private String content;
}
