package harmony.communityservice.guild.category.service.command.impl;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.guild.category.mapper.ToCategoryMapper;
import harmony.communityservice.guild.category.dto.DeleteCategoryRequest;
import harmony.communityservice.guild.category.dto.ModifyCategoryRequest;
import harmony.communityservice.guild.category.dto.RegisterCategoryRequest;
import harmony.communityservice.guild.category.repository.command.CategoryCommandRepository;
import harmony.communityservice.guild.category.service.command.CategoryCommandService;
import harmony.communityservice.guild.category.service.query.CategoryQueryService;
import harmony.communityservice.guild.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class CategoryCommandServiceImpl implements CategoryCommandService {
    private final CategoryCommandRepository categoryCommandRepository;
    private final CategoryQueryService categoryQueryService;

    @Override
    @AuthorizeGuildMember
    public void register(RegisterCategoryRequest registerCategoryRequest) {
        Category category = createCategory(registerCategoryRequest);
        categoryCommandRepository.save(category);
    }

    private Category createCategory(RegisterCategoryRequest registerCategoryRequest) {
        return ToCategoryMapper.convert(registerCategoryRequest);
    }

    @Override
    @AuthorizeGuildMember
    public void delete(DeleteCategoryRequest deleteCategoryRequest) {
        categoryCommandRepository.deleteByCategoryId(deleteCategoryRequest.categoryId());
    }

    @Override
    @AuthorizeGuildMember
    public void modify(ModifyCategoryRequest modifyCategoryRequest) {
        categoryQueryService.existsByCategoryIdAndGuildId(modifyCategoryRequest.categoryId(),
                modifyCategoryRequest.guildId());
        modifyCategory(modifyCategoryRequest);
    }


    private void modifyCategory(ModifyCategoryRequest modifyCategoryRequest) {
        Category targetCategory = categoryQueryService.searchByCategoryId(modifyCategoryRequest.categoryId());
        targetCategory.modifyName(modifyCategoryRequest.name());
    }
}
