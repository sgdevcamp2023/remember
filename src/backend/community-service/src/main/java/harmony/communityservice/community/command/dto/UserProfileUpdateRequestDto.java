package harmony.communityservice.community.command.dto;

import lombok.Getter;

@Getter
public class UserProfileUpdateRequestDto {

    private Long userId;
    private String profile;
}
