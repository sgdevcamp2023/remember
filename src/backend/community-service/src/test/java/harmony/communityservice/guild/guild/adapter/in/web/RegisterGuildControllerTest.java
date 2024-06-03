package harmony.communityservice.guild.guild.adapter.in.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import harmony.communityservice.guild.guild.application.port.in.RegisterGuildCommand;
import harmony.communityservice.guild.guild.application.port.in.RegisterGuildUseCase;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = RegisterGuildController.class)
class RegisterGuildControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterGuildUseCase registerGuildUseCase;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("길드 등록 API 테스트")
    void register_guild() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        RegisterGuildRequest registerGuildRequest = new RegisterGuildRequest(1L, "test_guild");
        BaseResponse<Object> baseResponse = new BaseResponse<>(200, "OK", 1L);
        MockMultipartFile profile = new MockMultipartFile("profile", "test-profile.jpg", "image/jpeg",
                "test image content".getBytes());
        MockMultipartFile request = new MockMultipartFile("registerGuildRequest", null,
                "application/json",
                objectMapper.writeValueAsString(registerGuildRequest).getBytes(StandardCharsets.UTF_8));
        RegisterGuildCommand registerGuildCommand = new RegisterGuildCommand(profile, 1L, "test_guild");
        given(registerGuildUseCase.register(registerGuildCommand)).willReturn(1L);

        mockMvc.perform(multipart("/api/community/register/guild")
                        .file(profile)
                        .file(request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(baseResponse)));

        then(registerGuildUseCase).should()
                .register(registerGuildCommand);
    }

}