package harmony.communityservice.guild.category.repository.command;

import harmony.communityservice.guild.domain.Category;
import java.util.Optional;

public interface CategoryCommandRepository {

    Optional<Category> findById(Long categoryId);
    Long save(Category category);

    void deleteById(Long categoryId);

    void deleteByGuildId(Long guildId);
}
