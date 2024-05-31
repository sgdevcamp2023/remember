package harmony.communityservice.guild.guild.adapter.in.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import harmony.communityservice.guild.guild.application.port.in.LoadInvitationCodeCommand;
import harmony.communityservice.guild.guild.application.port.in.LoadInvitationCodeQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = LoadInvitationCodeController.class)
class LoadInvitationCodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoadInvitationCodeQuery loadInvitationCodeQuery;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("길드 초대 코드 조회 API 테스트")
    void load_invitation_code() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        LoadGuildInvitationCodeRequest loadGuildInvitationCodeRequest = new LoadGuildInvitationCodeRequest(1L,
                1L);
        LoadInvitationCodeCommand loadInvitationCodeCommand = new LoadInvitationCodeCommand(1L, 1L);
        given(loadInvitationCodeQuery.loadInvitationCode(loadInvitationCodeCommand)).willReturn("code.1.1");
        BaseResponse<String> baseResponse = new BaseResponse<>(200, "OK", "code.1.1");

        mockMvc.perform(post("/api/community/search/invitation/code/guild")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loadGuildInvitationCodeRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(baseResponse)));

        then(loadInvitationCodeQuery).should().loadInvitationCode(loadInvitationCodeCommand);
    }
}