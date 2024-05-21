package harmony.communityservice.guild.category.application.service;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.common.annotation.UseCase;
import harmony.communityservice.guild.category.application.port.in.DeleteCategoryCommand;
import harmony.communityservice.guild.category.application.port.in.DeleteCategoryUseCase;
import harmony.communityservice.guild.category.application.port.in.DeleteCategoryUsingGuildIdUseCase;
import harmony.communityservice.guild.category.application.port.in.ModifyCategoryCommand;
import harmony.communityservice.guild.category.application.port.in.ModifyCategoryUseCase;
import harmony.communityservice.guild.category.application.port.in.RegisterCategoryCommand;
import harmony.communityservice.guild.category.application.port.in.RegisterCategoryUseCase;
import harmony.communityservice.guild.category.application.port.out.DeleteCategoryPort;
import harmony.communityservice.guild.category.application.port.out.DeleteUsingGuildIdPort;
import harmony.communityservice.guild.category.application.port.out.ModifyCategoryPort;
import harmony.communityservice.guild.category.application.port.out.RegisterCategoryPort;
import harmony.communityservice.guild.category.domain.Category;
import harmony.communityservice.guild.category.domain.Category.CategoryId;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
class CategoryCommandService implements RegisterCategoryUseCase, DeleteCategoryUseCase, ModifyCategoryUseCase,
        DeleteCategoryUsingGuildIdUseCase {

    private final RegisterCategoryPort registerCategoryPort;
    private final DeleteCategoryPort deleteCategoryPort;
    private final ModifyCategoryPort modifyCategoryPort;
    private final DeleteUsingGuildIdPort deleteUsingGuildIdPort;

    @Override
    @AuthorizeGuildMember
    public void register(RegisterCategoryCommand registerCategoryCommand) {
        Category category = CategoryMapper.convert(registerCategoryCommand);
        registerCategoryPort.register(category);
    }

    @Override
    @AuthorizeGuildMember
    public void delete(DeleteCategoryCommand deleteCategoryCommand) {
        deleteCategoryPort.delete(CategoryId.make(deleteCategoryCommand.categoryId()));
    }

    @Override
    @AuthorizeGuildMember
    public void modify(ModifyCategoryCommand modifyCategoryCommand) {
        modifyCategoryPort.modify(CategoryId.make(modifyCategoryCommand.categoryId()), modifyCategoryCommand.name());
    }

    @Override
    public void deleteByGuildId(Long guildId) {
        deleteUsingGuildIdPort.deleteByGuildId(GuildId.make(guildId));
    }

}
