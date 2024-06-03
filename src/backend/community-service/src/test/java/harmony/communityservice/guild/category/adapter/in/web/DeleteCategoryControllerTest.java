package harmony.communityservice.guild.category.adapter.in.web;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import harmony.communityservice.guild.category.application.port.in.DeleteCategoryCommand;
import harmony.communityservice.guild.category.application.port.in.DeleteCategoryUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = DeleteCategoryController.class)
class DeleteCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeleteCategoryUseCase deleteCategoryUseCase;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("키테고리 삭제 API 테스트")
    void delete_category() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        DeleteCategoryRequest deleteCategoryRequest = new DeleteCategoryRequest(1L, 1L, 1L);
        BaseResponse<Object> baseResponse = new BaseResponse<>(200, "OK");
        DeleteCategoryCommand deleteCategoryCommand = new DeleteCategoryCommand(deleteCategoryRequest.guildId(),
                deleteCategoryRequest.categoryId(),
                deleteCategoryRequest.userId());

        mockMvc.perform(delete("/api/community/delete/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteCategoryRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(baseResponse)));

        then(deleteCategoryUseCase).should().delete(deleteCategoryCommand);
    }
}