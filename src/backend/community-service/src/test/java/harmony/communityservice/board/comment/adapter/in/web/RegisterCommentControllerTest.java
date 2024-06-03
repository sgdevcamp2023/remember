package harmony.communityservice.board.comment.adapter.in.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.board.comment.application.port.in.RegisterCommentCommand;
import harmony.communityservice.board.comment.application.port.in.RegisterCommentUseCase;
import harmony.communityservice.common.dto.BaseExceptionResponse;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@WebMvcTest(controllers = RegisterCommentController.class)
class RegisterCommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterCommentUseCase registerCommentUseCase;

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
    @DisplayName("댓글 등록 API 테스트")
    void register_comment() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        RegisterCommentRequest registerCommentRequest = new RegisterCommentRequest(1L, 1L, "first comment",
                "first_user", "first");
        BaseResponse<Object> baseResponse = new BaseResponse<>(200, "OK");
        RegisterCommentCommand registerCommentCommand = RegisterCommentCommand.builder()
                .boardId(registerCommentRequest.boardId())
                .userId(registerCommentRequest.userId())
                .writerName(registerCommentRequest.writerName())
                .comment(registerCommentRequest.comment())
                .writerProfile(registerCommentRequest.writerProfile())
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/community/register/board/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerCommentRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(baseResponse)));

        then(registerCommentUseCase).should().register(registerCommentCommand);
    }

    @Test
    @DisplayName("댓글 등록 API 예외 테스트")
    void register_comment_exception() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        RegisterCommentRequest registerCommentRequest = new RegisterCommentRequest(1L, 1L, "first comment",
                "first_user", "first");
        BaseResponse<Object> baseResponse = new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST",
                new BaseExceptionResponse("NOT_FOUND_DATA", 5000, "해당하는 데이터를 찾을 수 없습니다"));
        RegisterCommentCommand registerCommentCommand = RegisterCommentCommand.builder()
                .boardId(registerCommentRequest.boardId())
                .userId(registerCommentRequest.userId())
                .writerName(registerCommentRequest.writerName())
                .comment(registerCommentRequest.comment())
                .writerProfile(registerCommentRequest.writerProfile())
                .build();
        willThrow(NotFoundDataException.class).given(registerCommentUseCase).register(registerCommentCommand);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/community/register/board/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerCommentRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(baseResponse)));

    }
}