package harmony.communityservice.guild.category.mapper;

import harmony.communityservice.guild.domain.Category;
import harmony.communityservice.guild.category.dto.SearchCategoryResponse;

public class ToSearchCategoryResponseMapper {

    public static SearchCategoryResponse convert(Category category) {
        return SearchCategoryResponse.builder()
                .categoryName(category.getName())
                .guildId(category.getGuildId())
                .categoryId(category.getCategoryId())
                .build();
    }
}
