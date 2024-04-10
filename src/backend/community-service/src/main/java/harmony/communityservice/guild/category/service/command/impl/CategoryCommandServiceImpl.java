package harmony.communityservice.guild.category.service.command.impl;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.guild.category.dto.DeleteCategoryRequest;
import harmony.communityservice.guild.category.dto.ModifyCategoryRequest;
import harmony.communityservice.guild.category.dto.RegisterCategoryRequest;
import harmony.communityservice.guild.category.mapper.ToCategoryMapper;
import harmony.communityservice.guild.category.service.command.CategoryCommandService;
import harmony.communityservice.guild.domain.Category;
import harmony.communityservice.guild.domain.Guild;
import harmony.communityservice.guild.guild.repository.command.GuildCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class CategoryCommandServiceImpl implements CategoryCommandService {
    private final GuildCommandRepository guildCommandRepository;


    @Override
    @AuthorizeGuildMember
    public void registerCategory(RegisterCategoryRequest registerCategoryRequest) {
        Category category = ToCategoryMapper.convert(registerCategoryRequest);
        Guild targetGuild = guildCommandRepository.findById(registerCategoryRequest.guildId())
                .orElseThrow(NotFoundDataException::new);
        targetGuild.registerCategory(category);
        guildCommandRepository.save(targetGuild);
    }

    @Override
    @AuthorizeGuildMember
    public void delete(DeleteCategoryRequest deleteCategoryRequest) {
        Guild targetGuild = guildCommandRepository.findById(deleteCategoryRequest.guildId())
                .orElseThrow(NotFoundDataException::new);
        targetGuild.deleteCategory(deleteCategoryRequest.categoryId());
        guildCommandRepository.save(targetGuild);

    }

    @Override
    @AuthorizeGuildMember
    public void modify(ModifyCategoryRequest modifyCategoryRequest) {
        Guild targetGuild = guildCommandRepository.findById(modifyCategoryRequest.guildId())
                .orElseThrow(NotFoundDataException::new);
        targetGuild.modifyCategoryName(modifyCategoryRequest.categoryId(), modifyCategoryRequest.name());
        guildCommandRepository.save(targetGuild);
    }
}
