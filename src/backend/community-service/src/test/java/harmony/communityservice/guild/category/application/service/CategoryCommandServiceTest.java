package harmony.communityservice.guild.category.application.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import harmony.communityservice.domain.Threshold;
import harmony.communityservice.guild.category.application.port.in.DeleteCategoryCommand;
import harmony.communityservice.guild.category.application.port.in.ModifyCategoryCommand;
import harmony.communityservice.guild.category.application.port.in.RegisterCategoryCommand;
import harmony.communityservice.guild.category.application.port.out.DeleteCategoryPort;
import harmony.communityservice.guild.category.application.port.out.DeleteUsingGuildIdPort;
import harmony.communityservice.guild.category.application.port.out.ModifyCategoryPort;
import harmony.communityservice.guild.category.application.port.out.RegisterCategoryPort;
import harmony.communityservice.guild.category.domain.Category;
import harmony.communityservice.guild.category.domain.Category.CategoryId;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryCommandServiceTest {
    @Mock
    RegisterCategoryPort registerCategoryPort;
    @Mock
    DeleteCategoryPort deleteCategoryPort;
    @Mock
    ModifyCategoryPort modifyCategoryPort;
    @Mock
    DeleteUsingGuildIdPort deleteUsingGuildIdPort;
    CategoryCommandService categoryCommandService;


    @BeforeEach
    void setting() {
        categoryCommandService = new CategoryCommandService(registerCategoryPort, deleteCategoryPort,
                modifyCategoryPort, deleteUsingGuildIdPort);
    }

    @Test
    @DisplayName("카테고리 등록 테스트")
    void register_category() {
        assertNotNull(categoryCommandService);

        Category category = Category.builder()
                .categoryId(CategoryId.make(Threshold.MIN.getValue()))
                .name("test_category")
                .guildId(GuildId.make(1L))
                .build();
        willDoNothing().given(registerCategoryPort).register(category);

        categoryCommandService.register(new RegisterCategoryCommand("test_category", 1L, 1L));

        then(registerCategoryPort).should(times(1)).register(category);
    }

    @Test
    @DisplayName("카테고리 삭제 테스트")
    void delete_category() {
        assertNotNull(categoryCommandService);

        willDoNothing().given(deleteCategoryPort).delete(CategoryId.make(1L));

        categoryCommandService.delete(new DeleteCategoryCommand(1L, 1L, 1L));

        then(deleteCategoryPort).should(times(1)).delete(CategoryId.make(1L));
    }

    @Test
    @DisplayName("카테고리 수정 테스트")
    void modify_category() {
        assertNotNull(categoryCommandService);
        willDoNothing().given(modifyCategoryPort).modify(CategoryId.make(1L), "new_category");

        categoryCommandService.modify(new ModifyCategoryCommand(1L, 1L, 1L, "new_category"));

        then(modifyCategoryPort).should(times(1)).modify(CategoryId.make(1L), "new_category");
    }

    @Test
    @DisplayName("길드 내의 카테고리 삭제 테스트")
    void delete_categories() {
        assertNotNull(categoryCommandService);
        willDoNothing().given(deleteUsingGuildIdPort).deleteByGuildId(GuildId.make(1L));

        categoryCommandService.deleteByGuildId(1L);

        then(deleteUsingGuildIdPort).should(times(1)).deleteByGuildId(GuildId.make(1L));
    }
}