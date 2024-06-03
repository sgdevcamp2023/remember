package harmony.communityservice.common.client.impl;

import harmony.communityservice.common.client.UserStatusClient;
import harmony.communityservice.common.client.feign.UserStatusFeignClient;
import harmony.communityservice.common.dto.LoadDmUserStateFeignResponse;
import harmony.communityservice.common.dto.LoadUserStateInGuildAndChannelFeignResponse;
import harmony.communityservice.common.dto.LoadUserStatusInDmRoomRequest;
import harmony.communityservice.guild.guild.adapter.in.web.LoadUserStatesInGuildRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserStatusClientImpl implements UserStatusClient {

    private final UserStatusFeignClient userStatusFeignClient;

    @Override
    public LoadUserStateInGuildAndChannelFeignResponse getCommunityUsersState(
            LoadUserStatesInGuildRequest loadUserStatesInGuildRequest) {
        return userStatusFeignClient.getCommunityUsersState(loadUserStatesInGuildRequest);
    }

    @Override
    public LoadDmUserStateFeignResponse userStates(
            LoadUserStatusInDmRoomRequest loadUserStatusInDmRoomRequest) {
        return userStatusFeignClient.userStates(loadUserStatusInDmRoomRequest);
    }
}
