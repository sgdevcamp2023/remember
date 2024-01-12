package harmony.communityservice.community.command.dto;

import lombok.Getter;

@Getter
public class UserNicknameUpdateRequestDto {

    private Long userId;
    private String nickname;
}
