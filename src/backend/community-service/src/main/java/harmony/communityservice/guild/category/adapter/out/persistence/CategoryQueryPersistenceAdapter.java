package harmony.communityservice.guild.category.adapter.out.persistence;

import harmony.communityservice.common.annotation.PersistenceAdapter;
import harmony.communityservice.guild.category.application.port.out.LoadListPort;
import harmony.communityservice.guild.category.domain.Category;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import java.util.List;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class CategoryQueryPersistenceAdapter implements LoadListPort {

    private final CategoryQueryRepository categoryQueryRepository;

    @Override
    public List<Category> loadList(GuildId guildId) {
        List<CategoryEntity> categoryEntities = categoryQueryRepository.findCategoriesByGuildId(
                GuildIdJpaVO.make(guildId.getId()));
        return categoryEntities.stream()
                .map(CategoryMapper::convert)
                .toList();
    }
}
