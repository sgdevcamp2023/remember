package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.community.command.dto.DeleteCategoryRequest;
import harmony.communityservice.community.command.dto.ModifyCategoryRequest;
import harmony.communityservice.community.command.dto.RegisterCategoryRequest;
import harmony.communityservice.community.command.repository.CategoryCommandRepository;
import harmony.communityservice.community.command.service.CategoryCommandService;
import harmony.communityservice.community.command.service.CategoryReadCommandService;
import harmony.communityservice.community.domain.Category;
import harmony.communityservice.community.domain.CategoryRead;
import harmony.communityservice.community.domain.Guild;
import harmony.communityservice.community.mapper.ToCategoryMapper;
import harmony.communityservice.community.query.service.CategoryQueryService;
import harmony.communityservice.community.query.service.CategoryReadQueryService;
import harmony.communityservice.community.query.service.GuildQueryService;
import harmony.communityservice.community.query.service.UserReadQueryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryCommandServiceImpl implements CategoryCommandService {

    private final GuildQueryService guildQueryService;
    private final UserReadQueryService userReadQueryService;
    private final CategoryCommandRepository categoryCommandRepository;
    private final CategoryReadCommandService categoryReadCommandService;
    private final CategoryReadQueryService categoryReadQueryService;
    private final CategoryQueryService categoryQueryService;

    @Override
    public void register(RegisterCategoryRequest registerCategoryRequest) {
        userReadQueryService.existsByUserIdAndGuildId(registerCategoryRequest.userId(),
                registerCategoryRequest.guildId());
        Category category = createCategory(registerCategoryRequest);
        categoryCommandRepository.save(category);
        categoryReadCommandService.register(category, registerCategoryRequest.guildId());
    }

    private Category createCategory(RegisterCategoryRequest registerCategoryRequest) {
        Guild targetGuild = guildQueryService.searchByGuildId(registerCategoryRequest.guildId());
        return ToCategoryMapper.convert(targetGuild, registerCategoryRequest);
    }

    @Override
    public void delete(DeleteCategoryRequest deleteCategoryRequest) {
        userReadQueryService.existsByUserIdAndGuildId(deleteCategoryRequest.userId(),
                deleteCategoryRequest.guildId());
        categoryCommandRepository.deleteByCategoryId(deleteCategoryRequest.categoryId());
        categoryReadCommandService.delete(deleteCategoryRequest.categoryId());
    }

    @Override
    public void modify(ModifyCategoryRequest modifyCategoryRequest) {
        userReadQueryService.existsByUserIdAndGuildId(modifyCategoryRequest.userId(),
                modifyCategoryRequest.guildId());
        categoryReadQueryService.existsByCategoryIdAndGuildId(modifyCategoryRequest.categoryId(),
                modifyCategoryRequest.guildId());
        modifyCategory(modifyCategoryRequest);
        modifyCategoryRead(modifyCategoryRequest);
    }

    private void modifyCategoryRead(ModifyCategoryRequest modifyCategoryRequest) {
        CategoryRead targetCategoryRead = categoryReadQueryService.searchByCategoryId(
                modifyCategoryRequest.categoryId());
        targetCategoryRead.modifyName(modifyCategoryRequest.name());
    }

    private void modifyCategory(ModifyCategoryRequest modifyCategoryRequest) {
        Category targetCategory = categoryQueryService.searchByCategoryId(modifyCategoryRequest.categoryId());
        targetCategory.modifyName(modifyCategoryRequest.name());
    }
}
