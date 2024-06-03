package harmony.communityservice.guild.guild.adapter.in.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import harmony.communityservice.guild.guild.application.port.in.JoinGuildCommand;
import harmony.communityservice.guild.guild.application.port.in.JoinGuildUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = JoinGuildController.class)
class JoinGuildControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JoinGuildUseCase joinGuildUseCase;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("길드 가입 API 테스트")
    void join_guild() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse<Object> baseResponse = new BaseResponse<>(200, "OK");

        mockMvc.perform(get("/api/community/join/guild/{invitationCode}/{userId}", "guild.1.1", 2L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(baseResponse)));

        then(joinGuildUseCase).should().join(new JoinGuildCommand(2L, "guild.1.1"));
    }

}