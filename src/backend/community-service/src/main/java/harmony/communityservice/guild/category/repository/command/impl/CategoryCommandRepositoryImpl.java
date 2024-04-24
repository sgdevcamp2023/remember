package harmony.communityservice.guild.category.repository.command.impl;

import harmony.communityservice.guild.category.repository.command.CategoryCommandRepository;
import harmony.communityservice.guild.category.repository.command.jpa.JpaCategoryCommandRepository;
import harmony.communityservice.guild.category.domain.Category;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryCommandRepositoryImpl implements CategoryCommandRepository {

    private final JpaCategoryCommandRepository jpaCategoryCommandRepository;

    @Override
    public Optional<Category> findById(Long categoryId) {
        return jpaCategoryCommandRepository.findById(categoryId);
    }

    @Override
    public Long save(Category category) {
        return jpaCategoryCommandRepository.save(category).getCategoryId();
    }

    @Override
    public void deleteById(Long categoryId) {
        jpaCategoryCommandRepository.deleteCategoryByCategoryId(categoryId);
    }

    @Override
    public void deleteByGuildId(Long guildId) {
        jpaCategoryCommandRepository.deleteCategoriesByGuildId(guildId);
    }
}
