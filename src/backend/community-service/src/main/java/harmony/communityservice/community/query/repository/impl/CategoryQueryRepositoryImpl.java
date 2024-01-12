package harmony.communityservice.community.query.repository.impl;

import harmony.communityservice.community.domain.Category;
import harmony.communityservice.community.query.repository.CategoryQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaCategoryQueryRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryQueryRepositoryImpl implements CategoryQueryRepository {

    private final JpaCategoryQueryRepository jpaCategoryQueryRepository;

    @Override
    public Optional<Category> findByCategoryId(Long categoryId) {
        return jpaCategoryQueryRepository.findById(categoryId);
    }
}
