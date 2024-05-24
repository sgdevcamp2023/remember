package harmony.communityservice.guild.category.domain;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.exception.WrongThresholdRangeException;
import harmony.communityservice.domain.Domain;
import harmony.communityservice.domain.Threshold;
import harmony.communityservice.domain.ValueObject;
import harmony.communityservice.guild.category.domain.Category.CategoryId;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Category extends Domain<Category, CategoryId> {

    private CategoryId categoryId;

    private GuildId guildId;

    private String name;

    @Builder
    public Category(CategoryId categoryId, GuildId guildId, String name) {
        verifyCategoryId(categoryId);
        this.categoryId = categoryId;
        verifyGuildId(guildId);
        this.guildId = guildId;
        verifyName(name);
        this.name = name;
    }

    private void verifyGuildId(GuildId guildId) {
        if (guildId == null) {
            throw new NotFoundDataException("guildId가 존재하지 않습니다");
        }

        if (guildId.getId() < Threshold.MIN.getValue()) {
            throw new WrongThresholdRangeException("guildId가 1 미만입니다.");
        }
    }

    private void verifyCategoryId(CategoryId categoryId) {
        if (categoryId != null && categoryId.getId() < Threshold.MIN.getValue()) {
            throw new WrongThresholdRangeException("categoryId가 1 미만입니다");
        }
    }

    private void verifyName(String name) {
        if (name == null) {
            throw new NotFoundDataException("name이 존재하지 않습니다");
        }
    }

    @Override
    public CategoryId getId() {
        return categoryId;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CategoryId extends ValueObject<CategoryId> {
        private final Long id;

        public static CategoryId make(Long id) {
            return new CategoryId(id);
        }

        @Override
        protected Object[] getEqualityFields() {
            return new Object[]{id};
        }
    }
}
