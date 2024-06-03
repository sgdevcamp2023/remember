package harmony.communityservice.guild.category.adapter.in.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import harmony.communityservice.guild.category.application.port.in.LoadCategoriesQuery;
import harmony.communityservice.guild.category.application.port.in.LoadCategoryResponse;
import harmony.communityservice.guild.category.application.port.in.LoadListCommand;
import harmony.communityservice.guild.category.domain.Category;
import harmony.communityservice.guild.category.domain.Category.CategoryId;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = LoadCategoryController.class)
class LoadCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoadCategoriesQuery loadCategoriesQuery;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("카테고리 리스트 조회 API 테스트")
    void load_categories() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
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
        LoadCategoryResponse loadCategoryResponse = new LoadCategoryResponse(categories);
        BaseResponse<LoadCategoryResponse> baseResponse = new BaseResponse<>(200, "OK", loadCategoryResponse);
        given(loadCategoriesQuery.loadList(new LoadListCommand(1L, 1L))).willReturn(loadCategoryResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/community/search/category/{guildId}/{userId}", 1L, 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(baseResponse)));

        then(loadCategoriesQuery).should().loadList(new LoadListCommand(1L, 1L));
    }
}