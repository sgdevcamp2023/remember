package harmony.communityservice.guild.category.adapter.out.persistence;

import harmony.communityservice.guild.category.domain.Category;
import harmony.communityservice.guild.category.domain.Category.CategoryId;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;

class CategoryMapper {
    static Category convert(CategoryEntity categoryEntity) {
        return Category.builder()
                .categoryId(CategoryId.make(categoryEntity.getCategoryId().getId()))
                .guildId(GuildId.make(categoryEntity.getGuildId().getId()))
                .name(categoryEntity.getName())
                .build();
    }
}
