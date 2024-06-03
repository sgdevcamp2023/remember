package harmony.communityservice.board.emoji.adapter.in.web;

import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.board.emoji.application.port.in.RegisterEmojiCommand;
import harmony.communityservice.board.emoji.application.port.in.RegisterEmojiUseCase;
import harmony.communityservice.common.dto.BaseExceptionResponse;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.exception.DuplicatedEmojiException;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@WebMvcTest(controllers = RegisterEmojiController.class)
class RegisterEmojiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterEmojiUseCase registerEmojiUseCase;

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
    @DisplayName("이모지 등록 API 테스트")
    void register_emoji() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        RegisterEmojiRequest registerEmojiRequest = new RegisterEmojiRequest(1L, 1L, 1L);
        BaseResponse<Object> baseResponse = new BaseResponse<>(200, "OK");

        mockMvc.perform(post("/api/community/register/board/emoji")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerEmojiRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(baseResponse)));

        then(registerEmojiUseCase).should().register(new RegisterEmojiCommand(1L, 1L, 1L));
    }

    @Test
    @DisplayName("이모지 이중 등록 API 예외 테스트")
    void register_duplication_emoji() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        RegisterEmojiRequest registerEmojiRequest = new RegisterEmojiRequest(1L, 1L, 1L);
        BaseResponse<Object> baseResponse = new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST",
                new BaseExceptionResponse("DUPLICATED_EMOJI_REQUEST", 1000, "같은 이모지를 추가하실 수 없습니다"));
        willThrow(DuplicatedEmojiException.class).given(registerEmojiUseCase)
                .register(new RegisterEmojiCommand(1L, 1L, 1L));
        mockMvc.perform(post("/api/community/register/board/emoji")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerEmojiRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(baseResponse)));

        then(registerEmojiUseCase).should().register(new RegisterEmojiCommand(1L, 1L, 1L));
    }
}