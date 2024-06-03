package harmony.communityservice.guild.guild.adapter.in.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import harmony.communityservice.guild.guild.application.port.in.LoadUserGuildListQuery;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = LoadUserGuildListController.class)
class LoadUserGuildListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoadUserGuildListQuery loadUserGuildListQuery;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("길드 리스트 조회 API 테스트")
    void load_guilds() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        SearchGuildReadResponse first = SearchGuildReadResponse.builder()
                .guildReadId(1L)
                .guildId(1L)
                .userId(1L)
                .name("first")
                .profile("first_profile")
                .build();

        SearchGuildReadResponse second = SearchGuildReadResponse.builder()
                .guildReadId(2L)
                .guildId(2L)
                .userId(1L)
                .name("second")
                .profile("second_profile")
                .build();

        SearchGuildReadResponse third = SearchGuildReadResponse.builder()
                .guildReadId(3L)
                .guildId(3L)
                .userId(1L)
                .name("third")
                .profile("third_profile")
                .build();
        Map<Long, SearchGuildReadResponse> result = Map.of(1L, first, 2L, second, 3L, third);
        given(loadUserGuildListQuery.loadGuilds(1L)).willReturn(result);
        BaseResponse<? extends Map<Long, ?>> baseResponse = new BaseResponse<>(200, "OK", result);

        mockMvc.perform(get("/api/community/search/guild/{userId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(baseResponse)));

        then(loadUserGuildListQuery).should().loadGuilds(1L);
    }
}