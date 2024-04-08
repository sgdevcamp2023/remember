package harmony.communityservice.guild.category.repository.command.impl;


import harmony.communityservice.guild.category.repository.command.CategoryCommandRepository;
import harmony.communityservice.guild.category.repository.command.jpa.JpaCategoryCommandRepository;
import harmony.communityservice.guild.domain.Category;
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
