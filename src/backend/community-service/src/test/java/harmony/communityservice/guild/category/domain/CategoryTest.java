package harmony.communityservice.guild.category.domain;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.guild.category.domain.Category.CategoryId;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    @DisplayName("같은 식별자면 같은 객체로 인식 테스트")
    void same_category() {
        Category firstCategory = Category.builder()
                .categoryId(CategoryId.make(1L))
                .guildId(GuildId.make(1L))
                .name("test")
                .build();
        Category secondCategory = Category.builder()
                .categoryId(CategoryId.make(1L))
                .guildId(GuildId.make(2L))
                .name("test2")
                .build();

        boolean equals = firstCategory.equals(secondCategory);

        assertSame(true, equals);
    }

    @Test
    @DisplayName("다른 식별자면 다른 객체로 인식 테스트")
    void different_category() {
        Category firstCategory = Category.builder()
                .categoryId(CategoryId.make(2L))
                .guildId(GuildId.make(1L))
                .name("test")
                .build();
        Category secondCategory = Category.builder()
                .categoryId(CategoryId.make(1L))
                .guildId(GuildId.make(1L))
                .name("test")
                .build();

        boolean equals = firstCategory.equals(secondCategory);

        assertSame(false, equals);
    }

    @Test
    @DisplayName("guildId가 존재하지 않으면 예외 처리 테스트")
    void not_exists_guild_id() {
        assertThrows(NotFoundDataException.class, () -> Category.builder()
                .categoryId(CategoryId.make(2L))
                .name("test")
                .build());
    }

    @Test
    @DisplayName("name가 존재하지 않으면 예외 처리 테스트")
    void not_exists_name() {
        assertThrows(NotFoundDataException.class, () -> Category.builder()
                .categoryId(CategoryId.make(2L))
                .guildId(GuildId.make(1L))
                .build());
    }



}