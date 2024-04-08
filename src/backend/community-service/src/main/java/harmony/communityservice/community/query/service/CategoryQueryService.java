package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.Category;
import harmony.communityservice.community.query.dto.SearchParameterMapperRequest;
import harmony.communityservice.community.query.dto.SearchCategoryResponse;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryQueryService {

    Category searchByCategoryId(Long categoryId);

    void existsByCategoryIdAndGuildId(Long categoryId, long guildId);

    List<SearchCategoryResponse> searchListByGuildId(SearchParameterMapperRequest searchCategoryListRequest);
}
