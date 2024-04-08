package harmony.communityservice.guild.category.repository.query.jpa;

import harmony.communityservice.guild.domain.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCategoryQueryRepository extends JpaRepository<Category, Long> {

    boolean existsByCategoryIdAndGuildId(Long categoryId, long guildId);

    List<Category> findCategoriesByGuildId(Long guildId);
}
