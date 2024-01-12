package harmony.communityservice.community.query.repository;

import harmony.communityservice.community.domain.CategoryRead;
import java.util.List;
import java.util.Optional;

public interface CategoryReadQueryRepository {
    List<CategoryRead> findAllByGuildId(Long guildId);

    Optional<CategoryRead> findCategoryReadById(Long categoryId);

    boolean existsByCategoryIdAndGuildId(Long categoryId, Long guildId);
}
