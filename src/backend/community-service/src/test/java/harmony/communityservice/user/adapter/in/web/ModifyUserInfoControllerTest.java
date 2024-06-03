package harmony.communityservice.user.adapter.in.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import harmony.communityservice.user.application.port.in.ModifyUserInfoUseCase;
import harmony.communityservice.user.application.port.in.ModifyUserNicknameCommand;
import harmony.communityservice.user.application.port.in.ModifyUserProfileCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = ModifyUserInfoController.class)
class ModifyUserInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ModifyUserInfoUseCase modifyUserInfoUseCase;

    @MockBean
    ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("유저 프로필 수정 API 테스트")
    void modify_user_profile_api() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        ModifyUserProfileRequest modifyUserProfileRequest = new ModifyUserProfileRequest(1L, "new_profile");
        BaseResponse<Object> response = new BaseResponse<>(200, "OK");

        mockMvc.perform(patch("/api/community/modify/user/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyUserProfileRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(response)));
        then(modifyUserInfoUseCase).should()
                .modifyProfile(eq(new ModifyUserProfileCommand(1L, "new_profile")));
    }

    @Test
    @DisplayName("유저 닉네임 수정 API 테스트")
    void modify_user_nickname_api() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ModifyUserNicknameRequest modifyUserNicknameRequest = new ModifyUserNicknameRequest(1L, "new_nickname");
        BaseResponse<Object> response = new BaseResponse<>(200, "OK");

        mockMvc.perform(patch("/api/community/modify/user/nickname")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyUserNicknameRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(response)));

        then(modifyUserInfoUseCase).should()
                .modifyNickname(eq(new ModifyUserNicknameCommand(1L, "new_nickname")));
    }

}