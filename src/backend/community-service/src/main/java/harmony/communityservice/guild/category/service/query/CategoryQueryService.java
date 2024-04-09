package harmony.communityservice.guild.category.service.query;

import harmony.communityservice.guild.domain.Category;
import harmony.communityservice.common.dto.SearchParameterMapperRequest;
import harmony.communityservice.guild.category.dto.SearchCategoryResponse;
import java.util.List;

public interface CategoryQueryService {

    List<SearchCategoryResponse> searchListByGuildId(SearchParameterMapperRequest searchCategoryListRequest);
}
