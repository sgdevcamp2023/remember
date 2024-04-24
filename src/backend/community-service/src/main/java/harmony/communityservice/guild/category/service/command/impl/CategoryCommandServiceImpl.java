package harmony.communityservice.guild.category.service.command.impl;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.guild.category.dto.DeleteCategoryRequest;
import harmony.communityservice.guild.category.dto.ModifyCategoryRequest;
import harmony.communityservice.guild.category.dto.RegisterCategoryRequest;
import harmony.communityservice.guild.category.mapper.ToCategoryMapper;
import harmony.communityservice.guild.category.repository.command.CategoryCommandRepository;
import harmony.communityservice.guild.category.service.command.CategoryCommandService;
import harmony.communityservice.guild.category.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class CategoryCommandServiceImpl implements CategoryCommandService {

    private final CategoryCommandRepository categoryCommandRepository;


    @Override
    @AuthorizeGuildMember
    public Long register(RegisterCategoryRequest registerCategoryRequest) {
        Category category = ToCategoryMapper.convert(registerCategoryRequest);
        return categoryCommandRepository.save(category);
    }

    @Override
    @AuthorizeGuildMember
    public void delete(DeleteCategoryRequest deleteCategoryRequest) {
        categoryCommandRepository.deleteById(deleteCategoryRequest.categoryId());
    }

    @Override
    @AuthorizeGuildMember
    public void modify(ModifyCategoryRequest modifyCategoryRequest) {
        Category targetCategory = categoryCommandRepository.findById(modifyCategoryRequest.categoryId())
                .orElseThrow(NotFoundDataException::new);
        targetCategory.modifyName(modifyCategoryRequest.name());
    }

    @Override
    public void deleteByGuildId(Long guildId) {
        categoryCommandRepository.deleteByGuildId(guildId);
    }
}
