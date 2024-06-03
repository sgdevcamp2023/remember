package harmony.communityservice.guild.channel.adapter.in.web;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import harmony.communityservice.guild.channel.application.port.in.DeleteChannelCommand;
import harmony.communityservice.guild.channel.application.port.in.DeleteChannelUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = DeleteChannelController.class)
class DeleteChannelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeleteChannelUseCase deleteChannelUseCase;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("채널 삭제 API 테스트")
    void delete_channel() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        DeleteChannelRequest deleteChannelRequest = new DeleteChannelRequest(1L, 1L, 1L, "FORUM");
        BaseResponse<Object> baseResponse = new BaseResponse<>(200, "OK");
        DeleteChannelCommand deleteChannelCommand = DeleteChannelCommand.builder()
                .type(deleteChannelRequest.type())
                .guildId(deleteChannelRequest.guildId())
                .channelId(deleteChannelRequest.channelId())
                .userId(deleteChannelRequest.userId())
                .build();

        mockMvc.perform(delete("/api/community/delete/channel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteChannelRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(baseResponse)));

        then(deleteChannelUseCase).should().delete(deleteChannelCommand);

    }
}