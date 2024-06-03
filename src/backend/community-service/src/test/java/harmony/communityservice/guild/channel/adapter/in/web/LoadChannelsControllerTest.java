package harmony.communityservice.guild.channel.adapter.in.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import harmony.communityservice.guild.channel.application.port.in.LoadChannelResponse;
import harmony.communityservice.guild.channel.application.port.in.LoadChannelsCommand;
import harmony.communityservice.guild.channel.application.port.in.LoadChannelsQuery;
import harmony.communityservice.guild.channel.domain.ChannelType;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = LoadChannelsController.class)
class LoadChannelsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoadChannelsQuery loadChannelsQuery;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("채널 리스트 조회 API 테스트")
    void load_channels() throws Exception {
        LoadChannelResponse firstResponse = LoadChannelResponse.builder()
                .channelId(1L)
                .channelName("first")
                .channelType(ChannelType.FORUM)
                .categoryId(1L)
                .guildId(1L)
                .build();
        LoadChannelResponse secondResponse = LoadChannelResponse.builder()
                .channelId(2L)
                .channelName("second")
                .channelType(ChannelType.FORUM)
                .categoryId(1L)
                .guildId(1L)
                .build();
        LoadChannelResponse thirdResponse = LoadChannelResponse.builder()
                .channelId(3L)
                .channelName("third")
                .channelType(ChannelType.FORUM)
                .categoryId(1L)
                .guildId(1L)
                .build();
        Map<Long, LoadChannelResponse> result = Map.of(1L, firstResponse, 2L, secondResponse, 3L,
                thirdResponse);
        ObjectMapper objectMapper = new ObjectMapper();
        LoadChannelsCommand loadChannelsCommand = new LoadChannelsCommand(1L, 1L);
        BaseResponse<Map<Long, LoadChannelResponse>> baseResponse = new BaseResponse<>(HttpStatus.OK.value(), "OK",
                result);
        given(loadChannelsQuery.loadChannels(loadChannelsCommand)).willReturn(result);

        mockMvc.perform(get("/api/community/search/channel/list/{guildId}/{userId}", 1L, 1L))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(baseResponse)));

        then(loadChannelsQuery).should().loadChannels(loadChannelsCommand);
    }
}