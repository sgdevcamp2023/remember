package harmony.communityservice.board.comment.adapter.in.web;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.board.comment.application.port.in.ModifyCommentCommand;
import harmony.communityservice.board.comment.application.port.in.ModifyCommentUseCase;
import harmony.communityservice.common.dto.BaseExceptionResponse;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@WebMvcTest(controllers = ModifyCommentController.class)
class ModifyCommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModifyCommentUseCase modifyCommentUseCase;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Autowired
    private WebApplicationContext ctx;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .build();
    }

    @Test
    @DisplayName("댓글 수정 API 테스트")
    void modify_comment() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ModifyCommentRequest modifyCommentRequest = new ModifyCommentRequest(1L, 1L, 1L, "NEW_COMMENT");
        BaseResponse<Object> baseResponse = new BaseResponse<>(200, "OK");
        ModifyCommentCommand modifyCommentCommand = ModifyCommentCommand.builder()
                .userId(modifyCommentRequest.userId())
                .boardId(modifyCommentRequest.boardId())
                .commentId(modifyCommentRequest.commentId())
                .comment(modifyCommentRequest.comment())
                .build();

        mockMvc.perform(patch("/api/community/modify/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyCommentRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(baseResponse)));

        then(modifyCommentUseCase).should().modify(modifyCommentCommand);
    }

    @Test
    @DisplayName("댓글 수정 API 예외 테스트")
    void modify_comment_exception() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String modifyCommentRequest = "{\n"
                + "\t\"userId\" : \"exception\",\n"
                + "\t\"commentId\" : 1,\n"
                + "    \"boardId\": 1,\n"
                + "\t\"comment\" : \"NEW_COMMENT\"\n"
                + "}";
        BaseResponse<Object> baseResponse = new BaseResponse<>(
                HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", new BaseExceptionResponse(
                "INVALID_INPUT", 5001, "잘못된 입력입니다"));

        mockMvc.perform(patch("/api/community/modify/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyCommentRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(baseResponse)));
    }
}