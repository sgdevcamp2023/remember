package harmony.communityservice.guild.category.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import harmony.communityservice.domain.Threshold;
import harmony.communityservice.guild.category.application.port.in.SearchCategoryResponse;
import harmony.communityservice.guild.category.application.port.in.SearchListCommand;
import harmony.communityservice.guild.category.application.port.out.LoadListPort;
import harmony.communityservice.guild.category.domain.Category;
import harmony.communityservice.guild.category.domain.Category.CategoryId;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryQueryServiceTest {

    @Mock
    LoadListPort loadListPort;
    CategoryQueryService categoryQueryService;

    @BeforeEach
    void setting() {
        categoryQueryService = new CategoryQueryService(loadListPort);
    }

    @Test
    @DisplayName("카테고리 리스트 조회 테스트")
    void load_categories() {
        assertNotNull(categoryQueryService);

        Category first = Category.builder()
                .categoryId(CategoryId.make(1L))
                .name("test_category")
                .guildId(GuildId.make(1L))
                .build();
        Category second = Category.builder()
                .categoryId(CategoryId.make(2L))
                .name("test_category")
                .guildId(GuildId.make(1L))
                .build();
        Category third = Category.builder()
                .categoryId(CategoryId.make(3L))
                .name("test_category")
                .guildId(GuildId.make(1L))
                .build();
        List<Category> categories = List.of(first, second, third);
        given(loadListPort.loadList(GuildId.make(1L))).willReturn(categories);

        SearchCategoryResponse searchCategoryResponse = categoryQueryService.loadList(new SearchListCommand(1L, 1L));

        assertEquals(searchCategoryResponse, new SearchCategoryResponse(categories));
        then(loadListPort).should(times(1)).loadList(GuildId.make(1L));

    }
}