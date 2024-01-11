package harmony.communityservice.community.query.repository.jpa;

import harmony.communityservice.community.domain.CategoryRead;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCategoryReadQueryRepository extends JpaRepository<CategoryRead, Long> {
    List<CategoryRead> findCategoryReadsByGuildId(Long guildId);
}
