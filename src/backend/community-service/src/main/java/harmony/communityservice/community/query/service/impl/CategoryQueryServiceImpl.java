package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.community.domain.Category;
import harmony.communityservice.community.query.repository.CategoryQueryRepository;
import harmony.communityservice.community.query.service.CategoryQueryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryQueryServiceImpl implements CategoryQueryService {

    private final CategoryQueryRepository categoryQueryRepository;

    @Override
    public Category findByCategoryId(Long categoryId) {
        return categoryQueryRepository.findByCategoryId(categoryId).orElseThrow(NotFoundDataException::new);
    }
}
