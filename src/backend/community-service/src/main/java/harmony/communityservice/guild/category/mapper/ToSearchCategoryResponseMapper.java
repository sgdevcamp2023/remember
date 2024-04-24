package harmony.communityservice.guild.category.mapper;

import harmony.communityservice.guild.category.domain.Category;
import harmony.communityservice.guild.category.dto.SearchCategoryResponse;

public class ToSearchCategoryResponseMapper {

    public static SearchCategoryResponse convert(Category category, Long guildId, Long categoryId) {
        return SearchCategoryResponse.builder()
                .categoryName(category.getName())
                .guildId(guildId)
                .categoryId(categoryId)
                .build();
    }
}
