package harmony.communityservice.guild.category.repository.query.jpa;

import harmony.communityservice.guild.category.domain.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCategoryQueryRepository extends JpaRepository<Category, Long> {

    List<Category> findCategoriesByGuildId(Long guildId);
}
