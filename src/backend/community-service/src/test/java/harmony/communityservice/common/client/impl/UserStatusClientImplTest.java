package harmony.communityservice.common.client.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import harmony.communityservice.common.client.feign.UserStatusFeignClient;
import harmony.communityservice.common.dto.LoadDmUserStateFeignResponse;
import harmony.communityservice.common.dto.LoadUserStateInGuildAndChannelFeignResponse;
import harmony.communityservice.common.dto.LoadUserStatusInDmRoomRequest;
import harmony.communityservice.guild.guild.adapter.in.web.LoadUserStatesInGuildRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserStatusClientImplTest {


    @Mock
    private UserStatusFeignClient userStatusFeignClient;

    private UserStatusClientImpl userStatusClient;

    @BeforeEach
    void setting() {
        userStatusClient = new UserStatusClientImpl(userStatusFeignClient);
    }

    @Test
    @DisplayName("길드 내 유저, 보이스 채팅 참여 유저 상태 조회 API 테스트")
    void load_user_states_guild() {
        LoadUserStatesInGuildRequest loadUserStatesInGuildRequest = new LoadUserStatesInGuildRequest(1L,
                List.of(1L, 2L, 3L));
        Map<Long, Set<Long>> channelStates = Map.of(1L, Set.of(1L, 2L), 2L, Set.of(3L));
        Map<Long, String> connectionStates = Map.of(1L, "ONLINE", 2L, "ONLINE", 3L, "ONLINE");
        LoadUserStateInGuildAndChannelFeignResponse loadUserStateInGuildAndRoomFeignResponse = new LoadUserStateInGuildAndChannelFeignResponse(
                channelStates, connectionStates);
        given(userStatusFeignClient.getCommunityUsersState(loadUserStatesInGuildRequest))
                .willReturn(loadUserStateInGuildAndRoomFeignResponse);

        LoadUserStateInGuildAndChannelFeignResponse communityUsersState = userStatusClient.getCommunityUsersState(
                loadUserStatesInGuildRequest);

        then(userStatusFeignClient).should(times(1)).getCommunityUsersState(loadUserStatesInGuildRequest);
        assertEquals(communityUsersState, loadUserStateInGuildAndRoomFeignResponse);
    }

    @Test
    @DisplayName("dm 룸 내 유저 상태 조회 API 테스트")
    void load_user_states_room() {
        LoadUserStatusInDmRoomRequest loadUserStatusInDmRoomRequest = new LoadUserStatusInDmRoomRequest(
                List.of(1L, 2L));
        Map<Long, String> connectionStates = Map.of(1L, "ONLINE", 2L, "ONLINE");
        LoadDmUserStateFeignResponse loadDmUserStateFeignResponse = new LoadDmUserStateFeignResponse(connectionStates);
        given(userStatusFeignClient.userStates(loadUserStatusInDmRoomRequest)).willReturn(loadDmUserStateFeignResponse);

        LoadDmUserStateFeignResponse feignResponse = userStatusClient.userStates(
                loadUserStatusInDmRoomRequest);

        then(userStatusFeignClient).should(times(1)).userStates(loadUserStatusInDmRoomRequest);
        assertEquals(feignResponse,loadDmUserStateFeignResponse);
    }
}