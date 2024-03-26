package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.RegisterCategoryRequest;
import harmony.communityservice.community.domain.Category;
import harmony.communityservice.community.domain.Guild;

public class ToCategoryMapper {

    public static Category convert(Guild guild, RegisterCategoryRequest requestDto) {
        return Category.builder()
                .name(requestDto.getName())
                .guild(guild)
                .build();
    }
}
