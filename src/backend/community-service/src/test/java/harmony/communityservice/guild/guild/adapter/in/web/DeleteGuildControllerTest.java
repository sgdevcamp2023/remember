package harmony.communityservice.guild.guild.adapter.in.web;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import harmony.communityservice.guild.guild.application.port.in.DeleteGuildCommand;
import harmony.communityservice.guild.guild.application.port.in.DeleteGuildUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = DeleteGuildController.class)
class DeleteGuildControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeleteGuildUseCase deleteGuildUseCase;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("길드 삭제 API 테스트")
    void delete_guild() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        DeleteGuildRequest deleteGuildRequest = new DeleteGuildRequest(1L, 1L);
        BaseResponse<Object> baseResponse = new BaseResponse<>(200, "OK");

        mockMvc.perform(delete("/api/community/delete/guild")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteGuildRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(baseResponse)));

        then(deleteGuildUseCase).should().delete(new DeleteGuildCommand(1L, 1L));
    }

}