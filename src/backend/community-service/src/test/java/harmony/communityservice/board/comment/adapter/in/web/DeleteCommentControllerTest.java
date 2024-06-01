package harmony.communityservice.board.comment.adapter.in.web;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.board.comment.application.port.in.DeleteCommentCommand;
import harmony.communityservice.board.comment.application.port.in.DeleteCommentUseCase;
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

@WebMvcTest(controllers = DeleteCommentController.class)
class DeleteCommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeleteCommentUseCase deleteCommentUseCase;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("댓글 삭제 API 테스트")
    void delete_comment() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        DeleteCommentRequest deleteCommentRequest = new DeleteCommentRequest(1L, 1L, 1L);
        BaseResponse<Object> baseResponse = new BaseResponse<>(200, "OK");

        mockMvc.perform(delete("/api/community/delete/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteCommentRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(baseResponse)));

        then(deleteCommentUseCase).should().delete(new DeleteCommentCommand(1L, 1L, 1L));
    }

}