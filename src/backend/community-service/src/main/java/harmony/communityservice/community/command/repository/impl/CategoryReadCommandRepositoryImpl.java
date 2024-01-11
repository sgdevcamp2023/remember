package harmony.communityservice.community.command.repository.impl;

import harmony.communityservice.community.command.repository.CategoryReadCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaCategoryReadCommandRepository;
import harmony.communityservice.community.domain.CategoryRead;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryReadCommandRepositoryImpl implements CategoryReadCommandRepository {

    private final JpaCategoryReadCommandRepository jpaCategoryReadCommandRepository;
    @Override
    public void save(CategoryRead categoryRead) {
        jpaCategoryReadCommandRepository.save(categoryRead);
    }

    @Override
    public void delete(Long categoryReadId) {
        jpaCategoryReadCommandRepository.deleteById(categoryReadId);
    }
}
