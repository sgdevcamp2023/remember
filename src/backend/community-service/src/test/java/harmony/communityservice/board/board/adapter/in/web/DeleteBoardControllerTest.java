package harmony.communityservice.board.board.adapter.in.web;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.board.board.application.port.in.DeleteBoardCommand;
import harmony.communityservice.board.board.application.port.in.DeleteBoardUseCase;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = DeleteBoardController.class)
class DeleteBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeleteBoardUseCase deleteBoardUseCase;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("게시글 삭제 테스트")
    void delete_board() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        DeleteBoardRequest deleteBoardRequest = new DeleteBoardRequest(1L, 1L);
        BaseResponse<Object> baseResponse = new BaseResponse<>(200, "OK");

        mockMvc.perform(delete("/api/community/delete/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteBoardRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(baseResponse)));

        then(deleteBoardUseCase).should().delete(new DeleteBoardCommand(1L, 1L));
    }
}