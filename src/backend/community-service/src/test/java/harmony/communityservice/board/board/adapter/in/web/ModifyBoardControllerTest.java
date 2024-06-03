package harmony.communityservice.board.board.adapter.in.web;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.board.board.application.port.in.ModifyBoardCommand;
import harmony.communityservice.board.board.application.port.in.ModifyBoardUseCase;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ModifyBoardController.class)
class ModifyBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModifyBoardUseCase modifyBoardUseCase;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("게시글 수정 API 테스트")
    void modify_board() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ModifyBoardRequest modifyBoardRequest = new ModifyBoardRequest(1L, 1L, "NEW_TITLE", "NEW_CONTENT");
        BaseResponse<Object> baseResponse = new BaseResponse<>(200, "OK");
        ModifyBoardCommand modifyBoardCommand = ModifyBoardCommand.builder()
                .boardId(modifyBoardRequest.boardId())
                .title(modifyBoardRequest.title())
                .userId(modifyBoardRequest.userId())
                .content(modifyBoardRequest.content())
                .build();

        mockMvc.perform(patch("/api/community/update/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyBoardRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(baseResponse)));

        then(modifyBoardUseCase).should().modify(modifyBoardCommand);
    }
}