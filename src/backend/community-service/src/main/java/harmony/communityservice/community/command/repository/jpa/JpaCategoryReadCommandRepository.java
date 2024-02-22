package harmony.communityservice.community.command.repository.jpa;

import harmony.communityservice.community.domain.CategoryRead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCategoryReadCommandRepository extends JpaRepository<CategoryRead, Long> {
}
