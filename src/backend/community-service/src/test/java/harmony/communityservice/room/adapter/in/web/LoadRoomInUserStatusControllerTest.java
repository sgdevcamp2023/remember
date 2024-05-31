package harmony.communityservice.room.adapter.in.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import harmony.communityservice.room.application.port.in.LoadUserStateResponse;
import harmony.communityservice.room.application.port.in.LoadUserStatesInRoomQuery;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = LoadRoomInUserStatusController.class)
class LoadRoomInUserStatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoadUserStatesInRoomQuery loadUserStatesInRoomQuery;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("dm룸 안의 유저 상태 조회 API 테스트")
    void load_room_user_states() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        LoadUserStateResponse firstState = new LoadUserStateResponse(1L, "first", "first", "ONLINE");
        LoadUserStateResponse secondState = new LoadUserStateResponse(2L, "second", "second", "ONLINE");
        Map<Long, LoadUserStateResponse> result = Map.of(1L, firstState, 2L, secondState);
        BaseResponse<Map<Long, LoadUserStateResponse>> baseResponse = new BaseResponse<>(200, "OK", result);
        given(loadUserStatesInRoomQuery.loadUserStates(1L)).willReturn(result);

        mockMvc.perform(get("/api/community/search/user/status/dm/{dmId}/{userId}", 1L, 1L))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(baseResponse)));

        then(loadUserStatesInRoomQuery).should()
                .loadUserStates(1L);
    }

}