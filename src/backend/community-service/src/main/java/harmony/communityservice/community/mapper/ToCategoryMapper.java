package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.RegisterCategoryRequest;
import harmony.communityservice.community.domain.Category;

public class ToCategoryMapper {

    public static Category convert(RegisterCategoryRequest registerCategoryRequest) {
        return Category.builder()
                .name(registerCategoryRequest.name())
                .guildId(registerCategoryRequest.guildId())
                .build();
    }
}
