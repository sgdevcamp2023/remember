package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.community.command.repository.CategoryReadCommandRepository;
import harmony.communityservice.community.command.service.CategoryReadCommandService;
import harmony.communityservice.community.domain.Category;
import harmony.communityservice.community.domain.CategoryRead;
import harmony.communityservice.community.mapper.ToCategoryReadMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryReadCommandServiceImpl implements CategoryReadCommandService {

    private final CategoryReadCommandRepository categoryReadCommandRepository;

    @Override
    public void register(Category category, Long guildId) {
        CategoryRead categoryRead = ToCategoryReadMapper.convert(category, guildId);
        categoryReadCommandRepository.save(categoryRead);
    }

    @Override
    public void delete(Long categoryId) {
        categoryReadCommandRepository.deleteByCategoryReadId(categoryId);
    }
}
