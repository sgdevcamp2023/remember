package harmony.communityservice.guild.category.service.command.impl;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.guild.category.domain.Category;
import harmony.communityservice.guild.category.domain.CategoryId;
import harmony.communityservice.guild.category.dto.DeleteCategoryRequest;
import harmony.communityservice.guild.category.dto.ModifyCategoryRequest;
import harmony.communityservice.guild.category.dto.RegisterCategoryRequest;
import harmony.communityservice.guild.category.mapper.ToCategoryMapper;
import harmony.communityservice.guild.category.repository.command.CategoryCommandRepository;
import harmony.communityservice.guild.category.service.command.CategoryCommandService;
import harmony.communityservice.guild.guild.domain.GuildId;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class CategoryCommandServiceImpl implements CategoryCommandService {

    private final CategoryCommandRepository categoryCommandRepository;


    @Override
    @AuthorizeGuildMember
    public void register(RegisterCategoryRequest registerCategoryRequest) {
        Category category = ToCategoryMapper.convert(registerCategoryRequest);
        categoryCommandRepository.save(category);
    }

    @Override
    @AuthorizeGuildMember
    public void delete(DeleteCategoryRequest deleteCategoryRequest) {
        categoryCommandRepository.deleteById(CategoryId.make(deleteCategoryRequest.categoryId()));
    }

    @Override
    @AuthorizeGuildMember
    public void modify(ModifyCategoryRequest modifyCategoryRequest) {
        Category targetCategory = categoryCommandRepository.findById(
                        CategoryId.make(modifyCategoryRequest.categoryId()))
                .orElseThrow(NotFoundDataException::new);
        targetCategory.modifyName(modifyCategoryRequest.name());
    }

    @Override
    public void deleteByGuildId(Long guildId) {
        categoryCommandRepository.deleteByGuildId(GuildId.make(guildId));
    }
}
