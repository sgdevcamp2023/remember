package harmony.communityservice.community.query.repository.jpa;

import harmony.communityservice.community.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCategoryQueryRepository extends JpaRepository<Category, Long> {
}
