package harmony.communityservice.guild.guild.adapter.in.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import harmony.communityservice.guild.guild.application.port.in.ModifyGuildNicknameCommand;
import harmony.communityservice.guild.guild.application.port.in.ModifyGuildNicknameUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = ModifyGuildController.class)
class ModifyGuildControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModifyGuildNicknameUseCase modifyGuildNicknameUseCase;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("길드 유저 닉네임 수정 API 테스트")
    void modify_user_nickname() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse<Object> baseResponse = new BaseResponse<>(200, "OK");
        ModifyUserNicknameInGuildRequest modifyUserNicknameInGuildRequest = new ModifyUserNicknameInGuildRequest(1L, 1L, "new_nickname");

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/community/modify/guild/username")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyUserNicknameInGuildRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(baseResponse)));

        then(modifyGuildNicknameUseCase).should()
                .modifyNickname(new ModifyGuildNicknameCommand(1L,1L,"new_nickname"));
    }


}