package harmony.communityservice.community.command.dto;

import lombok.Getter;

@Getter
public class CategoryDeleteRequestDto {

    private Long guildId;
    private Long categoryId;
    private Long userId;
}
