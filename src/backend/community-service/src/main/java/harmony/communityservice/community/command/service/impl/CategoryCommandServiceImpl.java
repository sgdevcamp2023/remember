package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.common.dto.VerifyGuildMemberRequest;
import harmony.communityservice.community.command.dto.DeleteCategoryRequest;
import harmony.communityservice.community.command.dto.ModifyCategoryRequest;
import harmony.communityservice.community.command.dto.RegisterCategoryRequest;
import harmony.communityservice.community.command.repository.CategoryCommandRepository;
import harmony.communityservice.community.command.service.CategoryCommandService;
import harmony.communityservice.community.domain.Category;
import harmony.communityservice.community.domain.Guild;
import harmony.communityservice.community.mapper.ToCategoryMapper;
import harmony.communityservice.community.query.service.CategoryQueryService;
import harmony.communityservice.community.query.service.GuildQueryService;
import harmony.communityservice.community.query.service.UserReadQueryService;
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
