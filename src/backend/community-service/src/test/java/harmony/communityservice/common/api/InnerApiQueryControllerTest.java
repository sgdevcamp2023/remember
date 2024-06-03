package harmony.communityservice.common.api;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.dto.LoadRoomsAndGuildsResponse;
import harmony.communityservice.common.service.LoadUserBelongsQuery;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = InnerApiQueryController.class)
class InnerApiQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoadUserBelongsQuery loadUserBelongsQuery;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("내부 API 테스트")
    void load_user_belongs() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        List<Long> guildIds = List.of(1L, 2L, 3L);
        List<Long> roomIds = List.of(1L, 2L, 3L);
        LoadRoomsAndGuildsResponse loadRoomsAndGuildsResponse = new LoadRoomsAndGuildsResponse(roomIds, guildIds);
        BaseResponse<LoadRoomsAndGuildsResponse> baseResponse = new BaseResponse<>(HttpStatus.OK.value(), "OK",
                loadRoomsAndGuildsResponse);

        given(loadUserBelongsQuery.load(1L)).willReturn(loadRoomsAndGuildsResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/community/search/rooms/guilds/{userId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(baseResponse)));

        then(loadUserBelongsQuery).should().load(1L);
    }

}