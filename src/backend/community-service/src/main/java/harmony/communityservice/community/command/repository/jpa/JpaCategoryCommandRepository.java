package harmony.communityservice.community.command.repository.jpa;

import harmony.communityservice.community.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCategoryCommandRepository extends JpaRepository<Category, Long> {
}
