package harmony.communityservice.room.adapter.in.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import harmony.communityservice.room.application.port.in.DeleteRoomCommand;
import harmony.communityservice.room.application.port.in.DeleteRoomUseCase;
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

@WebMvcTest(controllers = RoomDeleteController.class)
class RoomDeleteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeleteRoomUseCase deleteRoomUseCase;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("dm 룸 삭제 API 테스트")
    void delete_room() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        DeleteRoomRequest deleteRoomRequest = new DeleteRoomRequest(1L, 1L, 2L);
        BaseResponse<Object> response = new BaseResponse<>(200, "OK");

        mockMvc.perform(delete("/api/community/delete/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteRoomRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(response)));

        then(deleteRoomUseCase).should()
                .delete(new DeleteRoomCommand(1L,1L,2L));

    }

}