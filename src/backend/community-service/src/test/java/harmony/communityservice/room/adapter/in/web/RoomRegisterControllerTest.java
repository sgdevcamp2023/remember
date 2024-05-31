package harmony.communityservice.room.adapter.in.web;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import harmony.communityservice.room.application.port.in.RegisterRoomCommand;
import harmony.communityservice.room.application.port.in.RegisterRoomUseCase;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = RoomRegisterController.class)
class RoomRegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterRoomUseCase registerRoomUseCase;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("dm룸 저장 API 테스트")
    void register_room() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        RegisterRoomRequest registerRoomRequest = new RegisterRoomRequest("room", List.of(1L, 2L), "room_profile", 1L);
        BaseResponse<Object> response = new BaseResponse<>(200, "OK");

        mockMvc.perform(post("/api/community/register/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRoomRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(response)));

        RegisterRoomCommand registerRoomCommand = RegisterRoomCommand.builder()
                .name("room")
                .profile("room_profile")
                .members(List.of(1L, 2L))
                .userId(1L)
                .build();
        then(registerRoomUseCase).should()
                .register(registerRoomCommand);
    }

}