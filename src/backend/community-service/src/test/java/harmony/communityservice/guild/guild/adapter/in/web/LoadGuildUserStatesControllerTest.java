package harmony.communityservice.guild.guild.adapter.in.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildUserStatesCommand;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildUserStatesQuery;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildUserStatesResponse;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.guild.guild.domain.GuildRead.GuildReadId;
import harmony.communityservice.room.application.port.in.LoadUserStateResponse;
import harmony.communityservice.user.domain.User.UserId;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = LoadGuildUserStatesController.class)
class LoadGuildUserStatesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoadGuildUserStatesQuery loadGuildUserStatesQuery;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("길드의 유저 상태 리스트 조회 API 테스트")
    void load_guild_user_states() throws Exception {
        GuildRead first = GuildRead.builder()
                .userProfile("http://user.com/test1")
                .userNickname("0chord")
                .guildId(GuildId.make(1L))
                .guildReadId(GuildReadId.make(1L))
                .profile("http://guild.com/test1")
                .name("first_guild")
                .userId(UserId.make(1L))
                .build();

        GuildRead second = GuildRead.builder()
                .userProfile("http://user.com/test2")
                .userNickname("0Chord")
                .guildId(GuildId.make(1L))
                .guildReadId(GuildReadId.make(2L))
                .profile("http://guild.com/test2")
                .name("first_guild")
                .userId(UserId.make(2L))
                .build();

        GuildRead third = GuildRead.builder()
                .userProfile("http://user.com/test3")
                .userNickname("yh")
                .guildId(GuildId.make(1L))
                .guildReadId(GuildReadId.make(3L))
                .profile("http://guild.com/test3")
                .name("first_guild")
                .userId(UserId.make(3L))
                .build();

        LoadUserStateResponse firstUserState = LoadUserStateResponse.builder()
                .state("ONLINE")
                .userName("0chord")
                .userId(1L)
                .profile("http://user.com/test1")
                .build();
        LoadUserStateResponse secondUserState = LoadUserStateResponse.builder()
                .state("ONLINE")
                .userName("0Chord")
                .userId(2L)
                .profile("http://user.com/test2")
                .build();

        LoadUserStateResponse thirdUserState = LoadUserStateResponse.builder()
                .state("ONLINE")
                .userName("yh")
                .userId(3L)
                .profile("http://user.com/test3")
                .build();
        Map<Long, ?> guildStates = Map.of(1L, firstUserState, 2L, secondUserState, 3L,
                thirdUserState);
        Map<Long, Map<Long, ?>> voiceChannelStates = Map.of(1L, Map.of(1L, first, 2L, second, 3L, third));
        LoadGuildUserStatesResponse loadGuildUserStatesResponse = new LoadGuildUserStatesResponse(guildStates,
                voiceChannelStates);
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse<LoadGuildUserStatesResponse> baseResponse = new BaseResponse<>(HttpStatus.OK.value(), "OK",
                loadGuildUserStatesResponse);
        LoadGuildUserStatesCommand loadGuildUserStatesCommand = new LoadGuildUserStatesCommand(1L, 1L);
        given(loadGuildUserStatesQuery.load(loadGuildUserStatesCommand)).willReturn(loadGuildUserStatesResponse);

        mockMvc.perform(get("/api/community/search/user/status/guild/{guildId}/{userId}", 1L, 1L))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(baseResponse)));

        then(loadGuildUserStatesQuery).should().load(loadGuildUserStatesCommand);

    }
}