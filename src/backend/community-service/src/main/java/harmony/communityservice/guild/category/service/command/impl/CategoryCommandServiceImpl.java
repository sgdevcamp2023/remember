package harmony.communityservice.guild.category.service.command.impl;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.guild.category.dto.DeleteCategoryRequest;
import harmony.communityservice.guild.category.dto.ModifyCategoryRequest;
import harmony.communityservice.guild.category.dto.RegisterCategoryRequest;
import harmony.communityservice.guild.category.mapper.ToCategoryMapper;
import harmony.communityservice.guild.category.service.command.CategoryCommandService;
import harmony.communityservice.guild.category.service.query.CategoryQueryService;
import harmony.communityservice.guild.domain.Category;
import harmony.communityservice.guild.domain.Guild;
import harmony.communityservice.guild.guild.service.query.GuildQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class CategoryCommandServiceImpl implements CategoryCommandService {
    private final GuildQueryService guildQueryService;

    @Override
    @AuthorizeGuildMember
    public void register(RegisterCategoryRequest registerCategoryRequest) {
        Category category = ToCategoryMapper.convert(registerCategoryRequest);
        Guild targetGuild = guildQueryService.searchById(registerCategoryRequest.guildId());
        targetGuild.registerCategory(category);
    }

    @Override
    @AuthorizeGuildMember
    public void delete(DeleteCategoryRequest deleteCategoryRequest) {
        Guild targetGuild = guildQueryService.searchById(deleteCategoryRequest.guildId());
        targetGuild.deleteCategory(deleteCategoryRequest.categoryId());
    }

    @Override
    @AuthorizeGuildMember
    public void modify(ModifyCategoryRequest modifyCategoryRequest) {
        Guild targetGuild = guildQueryService.searchById(modifyCategoryRequest.guildId());
        targetGuild.modifyCategoryName(modifyCategoryRequest.categoryId(), modifyCategoryRequest.name());
    }
}
