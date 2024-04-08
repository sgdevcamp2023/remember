package harmony.communityservice.guild.category.repository.query;

import harmony.communityservice.guild.domain.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryQueryRepository {
    Optional<Category> findByCategoryId(Long categoryId);

    boolean existsByCategoryIdAndGuildId(Long categoryId, long guildId);

    List<Category> findCategoriesByGuildId(Long guildId);
}
