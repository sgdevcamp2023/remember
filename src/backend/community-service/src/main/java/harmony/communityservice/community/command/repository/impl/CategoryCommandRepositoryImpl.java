package harmony.communityservice.community.command.repository.impl;


import harmony.communityservice.community.command.repository.CategoryCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaCategoryCommandRepository;
import harmony.communityservice.community.domain.Category;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryCommandRepositoryImpl implements CategoryCommandRepository {

    private final JpaCategoryCommandRepository jpaCategoryCommandRepository;

    @Override
    public void save(Category category) {
        jpaCategoryCommandRepository.save(category);
    }

    @Override
    public void deleteByCategoryId(Long categoryId) {
        jpaCategoryCommandRepository.deleteById(categoryId);
    }
}
