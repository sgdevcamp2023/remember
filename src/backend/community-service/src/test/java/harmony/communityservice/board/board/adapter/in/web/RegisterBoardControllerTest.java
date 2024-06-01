package harmony.communityservice.board.board.adapter.in.web;

import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.board.board.application.port.in.RegisterBoardCommand;
import harmony.communityservice.board.board.application.port.in.RegisterBoardUseCase;
import harmony.communityservice.common.dto.BaseExceptionResponse;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.exception.IllegalGcsException;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@WebMvcTest(controllers = RegisterBoardController.class)
class RegisterBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterBoardUseCase registerBoardUseCase;

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
    @DisplayName("게시글 등록 API 테스트")
    void register_board() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        RegisterBoardRequest registerBoardRequest = new RegisterBoardRequest(1L, 1L, 1L, "title", "content");
        BaseResponse<Object> baseResponse = new BaseResponse<>(200, "OK");
        MockMultipartFile profile1 = new MockMultipartFile("images", "test-profile1.jpg", "image/jpeg",
                "test image content1".getBytes());
        MockMultipartFile profile2 = new MockMultipartFile("images", "test-profile2.jpg", "image/jpeg",
                "test image content2".getBytes());
        MockMultipartFile request = new MockMultipartFile("registerBoardRequest", null,
                "application/json",
                objectMapper.writeValueAsString(registerBoardRequest).getBytes(StandardCharsets.UTF_8));
        RegisterBoardCommand registerBoardCommand = RegisterBoardCommand.builder()
                .title(registerBoardRequest.title())
                .guildId(registerBoardRequest.guildId())
                .userId(registerBoardRequest.userId())
                .content(registerBoardRequest.content())
                .channelId(registerBoardRequest.channelId())
                .build();
        willDoNothing().given(registerBoardUseCase).register(registerBoardCommand, List.of(profile1, profile2));

        mockMvc.perform(multipart("/api/community/register/board")
                        .file(profile1)
                        .file(profile2)
                        .file(request))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(baseResponse)));

        then(registerBoardUseCase).should().register(registerBoardCommand, List.of(profile1, profile2));
    }

    @Test
    @DisplayName("게시글 등록 API 이미지 예외 테스트")
    void register_board_image_exception() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        RegisterBoardRequest registerBoardRequest = new RegisterBoardRequest(1L, 1L, 1L, "title", "content");
        BaseResponse<Object> baseResponse = new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST",
                new BaseExceptionResponse("INVALID_GCS_REQUEST", 5002, "잘못된 GCS 요청입니다"));
        MockMultipartFile profile1 = new MockMultipartFile("images", "test-profile1.jpg", "image/jpeg",
                "test image content1".getBytes());
        MockMultipartFile profile2 = new MockMultipartFile("images", "test-profile2.jpg", "image/jpeg",
                "test image content2".getBytes());
        MockMultipartFile request = new MockMultipartFile("registerBoardRequest", null,
                "application/json",
                objectMapper.writeValueAsString(registerBoardRequest).getBytes(StandardCharsets.UTF_8));
        RegisterBoardCommand registerBoardCommand = RegisterBoardCommand.builder()
                .title(registerBoardRequest.title())
                .guildId(registerBoardRequest.guildId())
                .userId(registerBoardRequest.userId())
                .content(registerBoardRequest.content())
                .channelId(registerBoardRequest.channelId())
                .build();
        willThrow(IllegalGcsException.class).given(registerBoardUseCase)
                .register(registerBoardCommand, List.of(profile1, profile2));

        mockMvc.perform(multipart("/api/community/register/board")
                        .file(profile1)
                        .file(profile2)
                        .file(request))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(baseResponse)));
    }

    @Test
    @DisplayName("게시글 등록 API 입력 예외 테스트")
    void register_board_input_exception() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String registerBoardRequest = "{\n"
                + "\t\"userId\" : \"exception\",\n"
                + "\t\"guildId\" : 1,\n"
                + "\t\"channelId\" : 3,\n"
                + "\t\"title\" : \"첫번째 게시글입니다\",\n"
                + "\t\"content\" : \"번째 개시글 테스트입니다.\"\n"
                + "}";
        BaseResponse<Object> baseResponse = new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST",
                new BaseExceptionResponse(
                        "INVALID_INPUT", 5001, "잘못된 입력입니다"));
        MockMultipartFile profile1 = new MockMultipartFile("images", "test-profile1.jpg", "image/jpeg",
                "test image content1".getBytes());
        MockMultipartFile profile2 = new MockMultipartFile("images", "test-profile2.jpg", "image/jpeg",
                "test image content2".getBytes());
        MockMultipartFile request = new MockMultipartFile("registerBoardRequest", null,
                "application/json",
                objectMapper.writeValueAsString(registerBoardRequest).getBytes(StandardCharsets.UTF_8));
        mockMvc.perform(multipart("/api/community/register/board")
                        .file(profile1)
                        .file(profile2)
                        .file(request))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(baseResponse)));
    }
}