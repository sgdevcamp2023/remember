package harmony.communityservice.community.query.repository.impl;

import harmony.communityservice.community.domain.CategoryRead;
import harmony.communityservice.community.query.repository.CategoryReadQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaCategoryReadQueryRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryReadQueryRepositoryImpl implements CategoryReadQueryRepository {

    private final JpaCategoryReadQueryRepository jpaCategoryReadQueryRepository;

    @Override
    public List<CategoryRead> findAllByGuildId(Long guildId) {
        return jpaCategoryReadQueryRepository.findCategoryReadsByGuildId(guildId);
    }

    @Override
    public Optional<CategoryRead> findCategoryReadById(Long categoryId) {
        return jpaCategoryReadQueryRepository.findById(categoryId);
    }

    @Override
    public boolean existsByCategoryIdAndGuildId(Long categoryId, Long guildId) {
        return jpaCategoryReadQueryRepository.existsCategoryReadByCategoryReadIdAndGuildId(categoryId, guildId);
    }
}