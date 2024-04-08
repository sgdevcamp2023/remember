package harmony.communityservice.guild.category.mapper;

import harmony.communityservice.guild.category.dto.RegisterCategoryRequest;
import harmony.communityservice.guild.domain.Category;

public class ToCategoryMapper {

    public static Category convert(RegisterCategoryRequest registerCategoryRequest) {
        return Category.builder()
                .name(registerCategoryRequest.name())
                .guildId(registerCategoryRequest.guildId())
                .build();
    }
}
