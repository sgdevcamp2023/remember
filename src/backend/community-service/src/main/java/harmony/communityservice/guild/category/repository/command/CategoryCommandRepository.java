package harmony.communityservice.guild.category.repository.command;

import harmony.communityservice.guild.domain.Category;

public interface CategoryCommandRepository {

    void save(Category category);

    void deleteByCategoryId(Long categoryId);
}
