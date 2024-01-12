package harmony.communityservice.community.command.dto;

import lombok.Getter;

@Getter
public class CategoryUpdateRequestDto {

    private Long guildId;
    private Long userId;
    private Long categoryId;
    private String name;
}
