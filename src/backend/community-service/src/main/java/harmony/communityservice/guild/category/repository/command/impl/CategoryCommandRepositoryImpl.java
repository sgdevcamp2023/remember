package harmony.communityservice.guild.category.repository.command.impl;

import harmony.communityservice.guild.category.domain.Category;
import harmony.communityservice.guild.category.domain.CategoryId;
import harmony.communityservice.guild.category.repository.command.CategoryCommandRepository;
import harmony.communityservice.guild.category.repository.command.jpa.JpaCategoryCommandRepository;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryCommandRepositoryImpl implements CategoryCommandRepository {

    private final JpaCategoryCommandRepository jpaCategoryCommandRepository;

    @Override
    public Optional<Category> findById(CategoryId categoryId) {
        return jpaCategoryCommandRepository.findById(categoryId);
    }

    @Override
    public void save(Category category) {
        jpaCategoryCommandRepository.save(category);
    }

    @Override
    public void deleteById(CategoryId categoryId) {
        jpaCategoryCommandRepository.deleteCategoryByCategoryId(categoryId);
    }

    @Override
    public void deleteByGuildId(GuildIdJpaVO guildId) {
        jpaCategoryCommandRepository.deleteCategoriesByGuildId(guildId);
    }
}
