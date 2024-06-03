package harmony.communityservice.room.adapter.in.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import harmony.communityservice.room.application.port.in.LoadRoomsQuery;
import harmony.communityservice.room.application.port.in.SearchRoomsResponse;
import harmony.communityservice.room.domain.Room;
import harmony.communityservice.room.domain.Room.RoomId;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = LoadRoomsController.class)
class LoadRoomsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoadRoomsQuery loadRoomsQuery;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("dm룸 리스트 조회 API 테스트")
    void load_rooms() throws Exception {
        Room firstRoom = Room.builder()
                .roomId(RoomId.make(1L))
                .name("first_room")
                .profile("http://first.com/room")
                .build();

        Room secondRoom = Room.builder()
                .roomId(RoomId.make(2L))
                .name("second_room")
                .profile("http://second.com/room")
                .build();
        SearchRoomsResponse searchRoomsResponse = new SearchRoomsResponse(List.of(firstRoom, secondRoom));
        BaseResponse<SearchRoomsResponse> roomsResponseBaseResponse = new BaseResponse<>(200, "OK",
                searchRoomsResponse);
        ObjectMapper objectMapper = new ObjectMapper();
        given(loadRoomsQuery.loadList(1L)).willReturn(searchRoomsResponse);

        mockMvc.perform(get("/api/community/search/rooms/{userId}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(objectMapper.writeValueAsString(roomsResponseBaseResponse)));

        then(loadRoomsQuery).should().loadList(1L);
    }

}