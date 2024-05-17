package harmony.communityservice.guild.category.adapter.out.persistence;

import harmony.communityservice.guild.category.domain.Category;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO;

class CategoryEntityMapper {

    static CategoryEntity convert(Category category) {
        return CategoryEntity.builder()
                .name(category.getName())
                .guildId(GuildIdJpaVO.make(category.getGuildId().getId()))
                .build();
    }
}
