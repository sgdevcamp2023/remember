package harmony.communityservice.community.command.dto;

import lombok.Getter;

@Getter
public class CategoryRegistrationRequestDto {
    private String name;
    private Long userId;
    private Long guildId;
}
