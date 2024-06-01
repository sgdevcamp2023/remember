package harmony.communityservice.guild.category.adapter.in.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import harmony.communityservice.guild.category.application.port.in.RegisterCategoryCommand;
import harmony.communityservice.guild.category.application.port.in.RegisterCategoryUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = RegisterCategoryController.class)
class RegisterCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterCategoryUseCase registerCategoryUseCase;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("카테고리 등록 API 테스트")
    void register_category() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        RegisterCategoryRequest registerCategoryRequest = new RegisterCategoryRequest("first", 1L, 1L);
        BaseResponse<Object> baseResponse = new BaseResponse<>(HttpStatus.OK.value(), "OK");

        mockMvc.perform(post("/api/community/register/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerCategoryRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(baseResponse)));

        then(registerCategoryUseCase).should().register(new RegisterCategoryCommand(registerCategoryRequest.name(),
                registerCategoryRequest.userId(),
                registerCategoryRequest.guildId()));

    }
}