package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.RegisterCategoryRequest;
import harmony.communityservice.community.domain.Category;
import harmony.communityservice.community.domain.Guild;

public class ToCategoryMapper {

    public static Category convert(Guild guild, RegisterCategoryRequest registerCategoryRequest) {
        return Category.builder()
                .name(registerCategoryRequest.name())
                .guild(guild)
                .build();
    }
}
