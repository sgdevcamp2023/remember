package harmony.communityservice.guild.category.adapter.out.persistence;

import harmony.communityservice.common.annotation.PersistenceAdapter;
import harmony.communityservice.guild.category.application.port.out.DeleteCategoryPort;
import harmony.communityservice.guild.category.application.port.out.DeleteUsingGuildIdPort;
import harmony.communityservice.guild.category.application.port.out.ModifyCategoryPort;
import harmony.communityservice.guild.category.application.port.out.RegisterCategoryPort;
import harmony.communityservice.guild.category.domain.Category;
import harmony.communityservice.guild.category.domain.Category.CategoryId;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class CategoryCommandPersistenceAdapter implements RegisterCategoryPort, DeleteCategoryPort, ModifyCategoryPort,
        DeleteUsingGuildIdPort {

    private final CategoryCommandRepository categoryCommandRepository;

    @Override
    public void register(Category category) {
        CategoryEntity categoryEntity = CategoryEntityMapper.convert(category);
        categoryCommandRepository.save(categoryEntity);
    }

    @Override
    public void delete(CategoryId categoryId) {
        categoryCommandRepository.deleteById(CategoryIdJpaVO.make(categoryId.getId()));
    }

    @Override
    public void modify(CategoryId categoryId, String newName) {
        categoryCommandRepository.modifyCategory(newName, CategoryIdJpaVO.make(categoryId.getId()));
    }

    @Override
    public void deleteByGuildId(GuildId guildId) {
        categoryCommandRepository.deleteCategoriesByGuildId(GuildIdJpaVO.make(guildId.getId()));
    }
}
