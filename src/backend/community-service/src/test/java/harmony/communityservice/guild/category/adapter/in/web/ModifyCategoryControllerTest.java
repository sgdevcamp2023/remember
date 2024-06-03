package harmony.communityservice.guild.category.adapter.in.web;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import harmony.communityservice.guild.category.application.port.in.ModifyCategoryCommand;
import harmony.communityservice.guild.category.application.port.in.ModifyCategoryUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ModifyCategoryController.class)
class ModifyCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModifyCategoryUseCase modifyCategoryUseCase;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("카테고리 수정 API 테스트")
    void modify_category() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ModifyCategoryRequest modifyCategoryRequest = new ModifyCategoryRequest(1L, 1L, 1L, "NEW_CATEGORY");
        BaseResponse<Object> baseResponse = new BaseResponse<>(200, "OK");
        ModifyCategoryCommand modifyCategoryCommand = new ModifyCategoryCommand(modifyCategoryRequest.guildId(),
                modifyCategoryRequest.userId(),
                modifyCategoryRequest.categoryId(), modifyCategoryRequest.name());

        mockMvc.perform(patch("/api/community/modify/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyCategoryRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(baseResponse)));

        then(modifyCategoryUseCase).should().modify(modifyCategoryCommand);
    }
}