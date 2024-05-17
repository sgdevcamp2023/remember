package harmony.communityservice.guild.category.repository.command;

import harmony.communityservice.guild.category.domain.Category;
import harmony.communityservice.guild.category.domain.CategoryId;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO;
import java.util.Optional;

public interface CategoryCommandRepository {

    Optional<Category> findById(CategoryId categoryId);
    void save(Category category);

    void deleteById(CategoryId categoryId);

    void deleteByGuildId(GuildIdJpaVO guildId);
}
