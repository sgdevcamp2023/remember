package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Category;
import harmony.communityservice.community.query.dto.SearchCategoryResponse;

public class ToSearchCategoryResponseMapper {

    public static SearchCategoryResponse convert(Category category) {
        return SearchCategoryResponse.builder()
                .categoryName(category.getName())
                .guildId(category.getGuildId())
                .categoryId(category.getCategoryId())
                .build();
    }
}
