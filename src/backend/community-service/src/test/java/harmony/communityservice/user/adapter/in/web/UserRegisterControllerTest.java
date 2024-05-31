package harmony.communityservice.user.adapter.in.web;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import harmony.communityservice.user.application.port.in.RegisterUserCommand;
import harmony.communityservice.user.application.port.in.RegisterUserUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = UserRegisterController.class)
class UserRegisterControllerTest {

    @MockBean
    RegisterUserUseCase registerUserUseCase;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("유저 등록 API 테스트")
    void register_user() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        RegisterUserRequest registerUserRequest = new RegisterUserRequest(1L, "example1@example1.com",
                "유저1", "https://storage.googleapis.com/sg-dev-remember-harmony/discord.png");
        BaseResponse<Object> response = new BaseResponse<>(200, "OK");

        mockMvc.perform(post("/api/community/register/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUserRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(response)));

        then(registerUserUseCase).should()
                .register(eq(new RegisterUserCommand(1L, "example1@example1.com",
                        "유저1", "https://storage.googleapis.com/sg-dev-remember-harmony/discord.png")));
    }
}