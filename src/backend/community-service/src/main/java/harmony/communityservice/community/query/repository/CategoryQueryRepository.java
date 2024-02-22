package harmony.communityservice.community.query.repository;

import harmony.communityservice.community.domain.Category;
import java.util.Optional;

public interface CategoryQueryRepository {
    Optional<Category> findByCategoryId(Long categoryId);
}
