package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.CategoryRead;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CategoryReadQueryService {

    List<CategoryRead> findCategoryReadsByGuildId(long guildId, long userId);
}
