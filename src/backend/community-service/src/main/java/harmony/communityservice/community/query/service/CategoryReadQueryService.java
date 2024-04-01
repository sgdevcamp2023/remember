package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.CategoryRead;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CategoryReadQueryService {

    List<CategoryRead> searchListByGuildId(long guildId, long userId);

    CategoryRead searchByCategoryId(long categoryId);

    void existsByCategoryIdAndGuildId(long categoryId, long guildId);
}
