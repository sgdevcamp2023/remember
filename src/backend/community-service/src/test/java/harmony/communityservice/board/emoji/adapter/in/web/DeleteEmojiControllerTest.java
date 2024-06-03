package harmony.communityservice.board.emoji.adapter.in.web;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.board.emoji.application.port.in.DeleteEmojiCommand;
import harmony.communityservice.board.emoji.application.port.in.DeleteEmojiUseCase;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = DeleteEmojiController.class)
class DeleteEmojiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeleteEmojiUseCase deleteEmojiUseCase;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("이모지 삭제 API 테스트")
    void delete_emoji() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        DeleteEmojiRequest deleteEmojiRequest = new DeleteEmojiRequest(1L, 1L);
        BaseResponse<Object> baseResponse = new BaseResponse<>(200, "OK");

        mockMvc.perform(delete("/api/community/delete/emoji")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteEmojiRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(baseResponse)));

        then(deleteEmojiUseCase).should().delete(new DeleteEmojiCommand(1L, 1L));

    }
}