package harmony.communityservice.community.command.dto;

import lombok.Getter;

@Getter
public class GuildUpdateNicknameRequestDto {

    private Long userId;
    private Long guildId;
    private String nickname;
}
