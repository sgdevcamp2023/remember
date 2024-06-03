package harmony.communityservice.guild.channel.adapter.in.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import harmony.communityservice.guild.channel.application.port.in.RegisterChannelCommand;
import harmony.communityservice.guild.channel.application.port.in.RegisterChannelUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = RegisterChannelController.class)
class RegisterChannelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterChannelUseCase registerChannelUseCase;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("채널 등록 API 테스트")
    void register_channel() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        RegisterChannelRequest guildChannelRequest = new RegisterChannelRequest(1L, "first", 1L, null, "FORUM");
        RegisterChannelRequest categoryChannelRequest = new RegisterChannelRequest(1L, "first", 1L, 1L, "FORUM");
        BaseResponse<Object> baseResponse = new BaseResponse<>(200, "OK");
        RegisterChannelCommand guildChannelCommand = RegisterChannelCommand.builder()
                .type(guildChannelRequest.type())
                .name(guildChannelRequest.name())
                .userId(guildChannelRequest.userId())
                .categoryId(guildChannelRequest.categoryId())
                .guildId(guildChannelRequest.guildId())
                .build();
        RegisterChannelCommand categoryChannelCommand = RegisterChannelCommand.builder()
                .type(categoryChannelRequest.type())
                .name(categoryChannelRequest.name())
                .userId(categoryChannelRequest.userId())
                .categoryId(categoryChannelRequest.categoryId())
                .guildId(categoryChannelRequest.guildId())
                .build();

        mockMvc.perform(post("/api/community/register/guild/channel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(guildChannelRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(baseResponse)));
        mockMvc.perform(post("/api/community/register/category/channel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryChannelRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(baseResponse)));

        then(registerChannelUseCase).should().register(eq(guildChannelCommand));
        then(registerChannelUseCase).should().register(eq(categoryChannelCommand));

    }
}