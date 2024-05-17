package harmony.communityservice.guild.category.domain;

import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Category {

    private CategoryId categoryId;

    private GuildId guildId;

    private String name;

    @Builder
    public Category(CategoryId categoryId, GuildId guildId, String name) {
        this.categoryId = categoryId;
        this.guildId = guildId;
        this.name = name;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CategoryId{
        private final Long id;

        public static CategoryId make(Long id) {
            return new CategoryId(id);
        }
    }
}
