package harmony.communityservice.guild.category.repository.query.impl;

import harmony.communityservice.guild.category.repository.query.CategoryQueryRepository;
import harmony.communityservice.guild.category.repository.query.jpa.JpaCategoryQueryRepository;
import harmony.communityservice.guild.domain.Category;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryQueryRepositoryImpl implements CategoryQueryRepository {

    private final JpaCategoryQueryRepository jpaCategoryQueryRepository;

    @Override
    public Optional<Category> findByCategoryId(Long categoryId) {
        return jpaCategoryQueryRepository.findById(categoryId);
    }

    @Override
    public boolean existsByCategoryIdAndGuildId(Long categoryId, long guildId) {
        return jpaCategoryQueryRepository.existsByCategoryIdAndGuildId(categoryId, guildId);
    }

    @Override
    public List<Category> findCategoriesByGuildId(Long guildId) {
        return jpaCategoryQueryRepository.findCategoriesByGuildId(guildId);
    }
}
