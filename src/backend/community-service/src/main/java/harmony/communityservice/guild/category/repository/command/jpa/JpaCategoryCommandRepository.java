package harmony.communityservice.guild.category.repository.command.jpa;

import harmony.communityservice.guild.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCategoryCommandRepository extends JpaRepository<Category, Long> {

    void deleteCategoryByCategoryId(Long categoryId);

    void deleteCategoriesByGuildId(Long guildId);
}
