package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.Category;
import harmony.communityservice.community.query.dto.SearchCategoryResponse;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CategoryQueryService {

    Category searchByCategoryId(Long categoryId);

    void existsByCategoryIdAndGuildId(Long categoryId, long guildId);

    List<SearchCategoryResponse> searchListByGuildId(Long guildId, Long userId);
}
