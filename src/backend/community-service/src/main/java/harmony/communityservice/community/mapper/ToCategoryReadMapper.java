package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Category;
import harmony.communityservice.community.domain.CategoryRead;

public class ToCategoryReadMapper {

    public static CategoryRead convert(Category category, Long guildId) {
        return CategoryRead.builder()
                .categoryReadId(category.getCategoryId())
                .guildId(guildId)
                .name(category.getName()).build();
    }
}
